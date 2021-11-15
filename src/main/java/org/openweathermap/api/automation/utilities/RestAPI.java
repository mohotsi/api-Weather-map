package org.openweathermap.api.automation.utilities;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import io.restassured.specification.RequestSpecification;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Locale;
import java.util.Map;

public class RestAPI {





    public    RequestSpecBuilder builder= new RequestSpecBuilder();

     String method;
     String baseUrl;

    public static final String apiKey="a82011d0ce314c4de5a891d2d37b73dc";



    public RestAPI(String baseUrl){

               this.baseUrl=baseUrl;
     }








    private ResponseOptions<Response> execute(String endPoint){
        builder.setBaseUri(this.baseUrl);
        builder.addQueryParam("appid",apiKey);
        RequestSpecification requestSpecification= builder.build();
        RequestSpecification request= RestAssured.given();
        request.contentType(ContentType.JSON);
        request.spec(requestSpecification);


        switch (method.toUpperCase()){
            case "GET"  :return request.get(endPoint);
            case "POST": return request.post(endPoint);
            case "PUT":return request.put(endPoint);
            case "DELETE":return request.delete(endPoint);
            default: throw new IllegalStateException("Method doesn't exist");

        }



    }
    public ResponseOptions<Response> post(String endPoint,Object body){
        method= "POST";
        builder.setBody(body);
       return execute(endPoint);
    }
    public ResponseOptions<Response> put(String endPoint,Object body){
        method= "PUT";
        builder.setBody(body);
        return execute(endPoint);
    }
    public ResponseOptions<Response> get(String endPoint){
        method= "GET";

        builder.setBaseUri(baseUrl);
      return   execute(endPoint);
    }

}
