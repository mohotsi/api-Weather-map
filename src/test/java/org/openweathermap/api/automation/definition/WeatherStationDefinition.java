package org.openweathermap.api.automation.definition;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import lombok.val;

import org.openweathermap.api.automation.models.ErrorMessage;
import org.openweathermap.api.automation.models.Station;
import org.openweathermap.api.automation.utilities.RestAPI;
import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class WeatherStationDefinition {
    @Value("${base.url}")
    private String baseUrl;
     ResponseOptions<Response> response;
    Station postBody;
    static String id;

    public void setResponse(ResponseOptions<Response> response) {
        this.response = response;
    }

    @Given("^Client perform post operation for end point \"([^\"]*)\" with body$")
        public void clientPerformOperationForEndPoint(String endPoint, DataTable dataTable){

            response= new RestAPI(baseUrl).post(endPoint,getExpectedStation(dataTable));
        id = response.thenReturn().as(Station.class).getID();


       }
    @Then("Response body data {string}")
    public void serverGiveConfirmationPostResponse(String condition, DataTable dataTable){
        if(condition.equalsIgnoreCase("should match with expected data below")) {
            val actual = response.thenReturn().as(Station.class);


            val expected = getExpectedStation(dataTable);
            expected.setID(id);

            assertThat("response body doesn't match expected data", actual,equalTo(expected) );


        }
        if(condition.equalsIgnoreCase("should return error message")){
            val actual = response.thenReturn().as(ErrorMessage.class);
            val expected = getExpectedErrorMessage(dataTable);

            assertEquals("there should be error message matching expected data",expected,actual);
        }
        if(condition.equalsIgnoreCase("contains")){

            val actual = Arrays.stream(response.thenReturn().as(Station[].class)).collect(Collectors.toList());
            var expected = getExpectedStation(dataTable);
            expected.setID(id);

            assertThat("the body does not contain the expected Station",actual,hasItem(expected));
        }
        if(condition.equalsIgnoreCase("does not contain")){

            val actual = Arrays.stream(response.thenReturn().as(Station[].class)).collect(Collectors.toList());
            var expected = getExpectedStation(dataTable);
            expected.setID(id);

            assertThat("the body contain the expected Station",actual,not(hasItem(expected)));
        }

        System.out.println("I did assertion");

    }

    @Then("Client perform get operation for end point {string}")
    public void clientGetTheNewlyRegisteredWeatherStationFor(String endPoint) {
        val client=new RestAPI(baseUrl);
        val pathVariable=endPoint.replaceAll(".+\\{(.*)\\}","$1");
        if(pathVariable.equalsIgnoreCase("id")) {
            client.builder.addPathParam("id", id);
            response=client.get(endPoint);
        }
        else {
            val path=    endPoint.replace("{","").replace("}","");
            response= client.get(path);
            System.out.println("");
        }
    }
    @Then("Client perform delete operation for end point {string}")
    public void clientDeleteTheNewlyRegisteredWeatherStationFor(String endPoint) {
        val client=new RestAPI(baseUrl);
        val pathVariable=endPoint.replaceAll(".+\\{(.*)\\}","$1");
        if(pathVariable.equalsIgnoreCase("id")) {
            client.builder.addPathParam("id", id);
           client.delete(endPoint);
        }
        else {
        val path=    endPoint.replace("{","").replace("}","");
          response= client.delete(path);
          System.out.println("");
        }
    }




    @Given("Client perform put operation for end point {string} with body")
    public void clientPerformPutOperationForEndPointWithBody(String endPoint,DataTable dataTable) {
        val data=getData(dataTable);

        val builder= Station.builder();

           Station body=  getExpectedStation(dataTable);
        val client=new RestAPI(baseUrl);
        client.builder.addPathParam("id", id);
        response=client.put(endPoint,body);
    }

    public Station getExpectedStation(DataTable dataTable){
        val data=getData(dataTable);

        val builder= Station.builder();
        Station expected=
                builder
                .external_id(data.get("external_id")).name(data.get("name"))
                .latitude(Double.parseDouble(data.get("latitude"))).longitude(Double.parseDouble(data.get("longitude")))
                .altitude(Double.parseDouble(data.get("altitude"))).build();
        return  expected;
    }

    public Map<String,String> getData(DataTable dataTable){
        val data=dataTable.asMaps().stream().findFirst().orElse(null);

        return data;
    }

    public ErrorMessage getExpectedErrorMessage(DataTable dataTable){
        val data=dataTable.asMaps().stream().findFirst().orElse(null);
        val builder= ErrorMessage.builder();
        val expected=builder.code(Integer.parseInt(data.get("code")))
                .message(data.get("message")).build();
        return  expected;
    }

    @Given("Client perform post operation for end point {string} with with empty body")
    public void clientPerformPostOperationForEndPointWithWithEmptyBody(String endPoint) {
        response= new RestAPI(baseUrl).post(endPoint,"");
    }
}
