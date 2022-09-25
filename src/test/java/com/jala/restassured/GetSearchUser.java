package com.jala.restassured;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.IOException;


public class GetSearchUser extends GenerateToken {

	Logger log = Logger.getLogger("devpinoyLogger");
    public static String bearerToken;

    @BeforeClass
    public  void setup()  {



        baseURI = prop.getProperty("SearchUrl");

        GenerateToken tokenObj=new GenerateToken();
        try {
            tokenObj.generateBearerToken();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        bearerToken=bearerToken.trim();
        System.out.println("Token in Create User API :"+bearerToken);



    }

    // Token generation using the JSON format in the body of the Request
    @Test
    public void SearchUsers() {

        boolean tokenValue = true;

        log.info("***********Searching the user************");
        Response response = given()
                .header("Authorization", "Bearer " +bearerToken)
                .header("Accept", "application/json")
                .contentType(ContentType.JSON).when()
                .get(baseURI).then().extract().response();

        //put(/1) is the id of the user - Search and pass that user here

        System.out.println("Bearer Token :" + bearerToken);
        System.out.println("Response Code : " + response.statusCode());

        if (bearerToken.isBlank()) {
            tokenValue = false;
        }

        System.out.println("Message : " + response.jsonPath().getString("message"));
        Assert.assertEquals(tokenValue, true);
        Assert.assertEquals(response.statusCode(), 201);
        Assert.assertEquals("User inserted successfully.", response.jsonPath().getString("message"));

    }

}
