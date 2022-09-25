package com.jala.restassured;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class PostCreateUser extends  GenerateToken {
	
	
	Logger log = Logger.getLogger("devpinoyLogger");

    @BeforeMethod
    public  void setup() {
        GenerateToken tokenObj=new GenerateToken();
        try {
            log.info("***********Generating the bearer token************");
        	tokenObj.generateBearerToken();
        } catch (IOException e) {
        	log.info("***Bearer token generation failed*****");
            throw new RuntimeException(e);
        }
        bearerToken=bearerToken.trim();
        System.out.println("Token in Create User API :"+bearerToken);

    }

    /**
     * Add Customize XML to pick the mentioned group from the test classes.
     * Below mentioned is the syntax of how to declare groups in XML file e.g.
     *
     *  <groups>
     *    <run>
     *     <include name="bonding" />
     *    </run>
     *   </groups>
     */
    @Test(priority = 1)
    public void createUserForValidInput() {

    	log.info("***Testing the Create user API for valid input*****");
    	
    	baseURI = prop.getProperty("UpdateUrl");
        boolean tokenValue = true;

        // creating a map with all the key value pairs of JSON request.

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("first_name", "JALA");
        map.put("last_name", "Academy");
        map.put("email", "jaaladem@gmail.com");
        map.put("mobile", "9794907788");
        map.put("dob", "2022-02-09");
        map.put("gender", "male");
        map.put("address", "Hyderabad");
        map.put("country", "india");


        String[] array = new String[2];
        array[0] = "aws";
        array[1] = "fullstack";

        map.put("skills", array);


        // Converting the map to json object
       /// JSONObject json = new JSONObject(map);
       // System.out.println("JSON from Map :" + json);

        Response response = given()
                .header("Authorization", "Bearer " +bearerToken)
                .header("Accept", "application/json")
                .contentType(ContentType.JSON).
                and()
                .body(map).when()
                .post(baseURI).then().extract().response();


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

    @Test(priority = 2)
    public void createUserForInvalidInput() {

        baseURI = prop.getProperty("UpdateUrl");

        boolean tokenValue = true;


        // creating a map with all the key value pairs of JSON request.

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("first_name", "JALA");
        map.put("last_name", "Academy");
        map.put("email", "jaaladem@gmail.com");
        map.put("mobile", "9794907778");
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
        System.out.println("JSON from Map :" + json);

        Response response = given()
                .header("Authorization", "Bearer " +bearerToken)
                .header("Accept", "application/json")
                .contentType(ContentType.JSON).and().body(json).when()
                .post(baseURI).then().extract().response();


        System.out.println("Bearer Token :" + bearerToken);
        System.out.println("Response Code : " + response.statusCode());

        if (bearerToken.isBlank()) {
            tokenValue = false;
        }

        System.out.println("Message : " + response.jsonPath().getString("message"));
        Assert.assertEquals(tokenValue, true);
        Assert.assertEquals(response.statusCode(), 422);
        Assert.assertEquals("The email has already been taken.", response.jsonPath().getString("message"));

    }

    /*
     * Test Script to test with the existing Mobile
     *
     */
    @Test(priority = 3)
    public void createUserExistingMobile() {

        baseURI = prop.getProperty("UpdateUrl");

        boolean tokenValue = true;


        // creating a map with all the key value pairs of JSON request.
        // Best example for the usage of Map, HashMap

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("first_name", "JALA");
        map.put("last_name", "Academy");
        map.put("email", "jaaladem@gmail.com");
        map.put("mobile", "1794907778");
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
                .post(baseURI).then().extract().response();


        System.out.println("Bearer Token :" + bearerToken);
        System.out.println("Response Code : " + response.statusCode());

        if (bearerToken.isBlank()) {
            tokenValue = false;
        }

        System.out.println("Message : " + response.jsonPath().getString("message"));
        Assert.assertEquals(tokenValue, true);
        Assert.assertEquals(response.statusCode(), 422);
        Assert.assertEquals("Mobile has already been taken.", response.jsonPath().getString("message"));

    }







}
