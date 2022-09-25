package com.jala.restassured;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.baseURI;


import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PutUpdateUser extends GenerateToken {

	Logger log = Logger.getLogger("devpinoyLogger");
    public static String bearerToken;

    @BeforeClass
    public static void setup() throws IOException {



        baseURI = prop.getProperty("SearchUrl");


        // Creating an object to GenerateToken for getting the BearerToken for
        // authentication


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
    public void createUserForValidInput() {

    	log.info("***********Create a user for valid input************");
    	boolean tokenValue = true;
        // creating a map with all the key value pairs of JSON request.
        // Best example for the usage of Map, HashMap

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("first_name", "JALA");
        map.put("last_name", "Academy");
        map.put("email", "jaalaacadem@gmaill.com");
        map.put("mobile", "1704907778");
        map.put("dob", "2022-02-09");
        map.put("gender", "male");
        map.put("address", "Hyderabad");
        map.put("country", "india");

        String[] array = new String[2];
        array[0] = "aws";
        array[1] = "fullstack";

        map.put("skills", array);


        // converting the map to json object
        JSONObject json = new JSONObject(map);
        System.out.println("JSON from MAp :" + json);

        Response response = given()
                .header("Authorization", "Bearer " +bearerToken)
                .header("Accept", "application/json")
                .contentType(ContentType.JSON).and().body(json).when()
                .put("/1").then().extract().response();

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
