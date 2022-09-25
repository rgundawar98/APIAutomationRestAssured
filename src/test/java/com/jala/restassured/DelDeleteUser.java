package com.jala.restassured;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class DelDeleteUser extends GenerateToken{
	
	Logger log = Logger.getLogger("devpinoyLogger");


    @BeforeMethod
    public  void setup() {
        GenerateToken tokenObj=new GenerateToken();
        try {
            tokenObj.generateBearerToken();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        bearerToken=bearerToken.trim();
        System.out.println("Token in Create User API :"+bearerToken);
    }

    @Test(groups = {"Regression"})
    public void deleteUserForValidInput() throws IOException {




        baseURI = prop.getProperty("UpdateUrl");

        boolean tokenValue = true;


        // creating a map with all the key value pairs of JSON request.

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("email", "jalaadem@gmail.com");

        // converting the map to json object
        JSONObject json = new JSONObject(map);
        System.out.println("JSON from Map :" + json);

        Response response = given()
                .header("Authorization", "Bearer " +bearerToken)
                .header("Accept", "application/json")
                .contentType(ContentType.JSON).
                and()
                .body(json).when()
                .delete(baseURI).then().extract().response();


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
