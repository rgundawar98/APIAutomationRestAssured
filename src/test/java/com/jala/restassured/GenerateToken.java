package com.jala.restassured;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.jala.restassured.restassured.BaseTest;
import io.restassured.http.ContentType;

import io.restassured.response.Response;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class GenerateToken extends BaseTest{
	
    /**
     * This bearer token should be used for all the API calls
     **/

	Logger log = Logger.getLogger("devpinoyLogger");
	public static String bearerToken;


    /**
     * Refer to base Test Class
     */
    @BeforeMethod

    public  void setupProperty() {
        BaseTest test=new BaseTest();
        try {
            test.propertySetup();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void generateBearerToken() throws IOException {

    	log.info("***********Generating the bearer token************");
    	baseURI = prop.getProperty("LoginUrl");

        //Maps the username and password to HashMap object

        Map<String, String> map = new HashMap<String, String>();
        map.put("email", prop.getProperty("username"));
        map.put("password", prop.getProperty("password"));

        // converting map to json object
        JSONObject json = new JSONObject(map);

        // given() method of RestAssured class to get a reference for RequestSpecification
        Response response = given()
                .header("Accept", "application/json")
                .contentType(ContentType.JSON)
                .and()
                .body(json.toString()).when()
                .post(baseURI).then()
                .extract()
                .response();

        int statusCode = response.statusCode();

        //  This status code value tells us if HTTP Response was successful or not.

        if (statusCode == 200) {

            bearerToken = response.jsonPath().getString("token"); // Stores the generated token
            System.out.println("Token Value :" + bearerToken);

        }
        else {
            /**
             *  400 Bad Request response status code
             * indicates that the server cannot or will not process the request due to something that is perceived to be a client error
             */
            System.out.println("Check Your Credentials");

        }

    }
}
