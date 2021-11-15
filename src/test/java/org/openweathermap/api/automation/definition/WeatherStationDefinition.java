package org.openweathermap.api.automation.definition;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import lombok.val;

import org.openweathermap.api.automation.models.Station;
import org.openweathermap.api.automation.utilities.RestAPI;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.AssertJUnit.assertTrue;

public class WeatherStationDefinition {
    @Value("${base.url}")
    private String baseUrl;
    static ResponseOptions<Response> response;
    Station postBody;
    static String id;


       @Given("^Client perform post operation for end point \"([^\"]*)\" with body$")
        public void clientPerformOperationForEndPoint(String endPoint, DataTable dataTable){
        val data=dataTable.asMaps().stream().findFirst().orElse(null);
        val builder= Station.builder();
          postBody =(Station) getExpected(dataTable);

          response= new RestAPI(baseUrl).post(endPoint,postBody);

       }
    @Then("Response body data {string} match per column with below column data below and have auto generated id")
    public void serverGiveConfirmationPostResponse(String condition, DataTable dataTable){

        val actual=response.thenReturn().as(Station.class);
        val expected=getExpected(dataTable);
        assertTrue("response body doesn't match expected data",actual.equals(expected)&& (actual.getID()!=null&& !actual.getID().isEmpty()));
        id=actual.getID();
        System.out.println("I did assertion");

    }

    @Then("Client perform get operation for end point {string}")
    public void clientGetTheNewlyRegisteredWeatherStationFor(String endPoint) {
        val client=new RestAPI(baseUrl);
        client.builder.addPathParam("id", id);
        response=client.get(endPoint);

       }
   /** @And("Client perform get operation for end point {string}")
    public void AndClientGetTheNewlyRegisteredWeatherStationFor(String endPoint) {
        clientGetTheNewlyRegisteredWeatherStationFor(endPoint);

    }**/



    @Given("Client perform put operation for end point {string} with body")
    public void clientPerformPutOperationForEndPointWithBody(String endPoint,DataTable dataTable) {
           Station body= (Station) getExpected(dataTable);
        val client=new RestAPI(baseUrl);
        client.builder.addPathParam("id", id);
        response=client.put(endPoint,body);
    }
    public Station getExpected(DataTable dataTable){
        val data=dataTable.asMaps().stream().findFirst().orElse(null);
        val builder= Station.builder();
        val expected=builder.external_id(data.get("external_id")).name(data.get("name"))
                .latitude(Double.parseDouble(data.get("latitude"))).longitude(Double.parseDouble(data.get("longitude")))
                .altitude(Integer.parseInt(data.get("altitude"))).build();
        return  expected;
    }
}
