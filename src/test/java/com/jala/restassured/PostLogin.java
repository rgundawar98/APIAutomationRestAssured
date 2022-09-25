package com.jala.restassured;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;


import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

public class PostLogin extends GenerateToken{

    /*
     * POST Login Testing the Login API with valid credentials
     *
     * Generate the token using username and password, Use the token to call other functions
     */
	Logger log = Logger.getLogger("devpinoyLogger");
	private static String requestBody;
    

    @BeforeMethod
    public static void setup() {

        RestAssured.baseURI = prop.getProperty("LoginUrl");
    }

    //Token generation using the JSON format in the body of the Request
    @Test
    public void TokenGenerationUsingJSONFormatInBody() {

    	log.info("***********TokenGenerationUsingJSONFormatInBody************");
    	boolean tokenValue=true;
        // Creating a request body like a string. It is not the best approach if the number of fields are more.
        requestBody = "{\n" +
                "  \"email\": \"admin@jalaacademy.com\",\n" +
                "  \"password\": \"admin123\" \n}";
        Response response = given()
                .header("Accept", "application/json")
                .contentType(ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .post(baseURI)
                .then()
                .extract()
                .response();

        Assert.assertEquals(200, response.statusCode());

        String token=response.jsonPath().getString("token");
        System.out.println("Token Value :"+token);

        if(token.isEmpty()) {
            tokenValue=false;
        }

        Assert.assertEquals(tokenValue, true);
        Assert.assertEquals("Login is successfully.", response.jsonPath().getString("message"));
        Assert.assertEquals("true", response.jsonPath().getString("status"));

    }

    //Token generation using the JSON format in the body of the Request
    @Test
    public void TokenGenerationUsingJSONObject() {

        /**
         * Following code is the Example to create a JSON object directly
         *
          */

        boolean tokenValue=false;
        JSONObject json = new JSONObject();
        json.put("email", "admin@jalaacademy.com");
        json.put("password", "admin123");


        Response response = given()
                .header("Accept", "application/json")
                .contentType(ContentType.JSON)
                .and()
                .body(json.toString())
                .when()
                .post(baseURI)
                .then()
                .extract()
                .response();

        Assert.assertEquals(200, response.statusCode());

        String token=response.jsonPath().getString("token");
        System.out.println("Token Value :"+token);

        if(!token.isEmpty()) {
            tokenValue=true;
        }

        Assert.assertEquals(tokenValue, true);
        Assert.assertEquals("Login is successfully.", response.jsonPath().getString("message"));
        Assert.assertEquals("true", response.jsonPath().getString("status"));

    }



    //Token generation using the MAP  in the body of the Request
    @Test
    public void TokenGenerationUsingMapObject() {

        boolean tokenValue=false;
        Map<String,String> map=new HashMap<String,String>();
        map.put("email", "admin@jalaacademy.com");
        map.put("password", "admin123");

        JSONObject json = new JSONObject(map);
        Response response = given()
                .header("Accept", "application/json")
                .contentType(ContentType.JSON)
                .and()
                .body(json.toString())
                .when()
                .post(baseURI)
                .then()
                .extract()
                .response();

        Assert.assertEquals(200, response.statusCode());

        String token=response.jsonPath().getString("token");
        System.out.println("Token Value :"+token);

        if(!token.isEmpty()) {
            tokenValue=true;
        }

        Assert.assertEquals(tokenValue, true);
        Assert.assertEquals("Login is successfully.", response.jsonPath().getString("message"));
        Assert.assertEquals("true", response.jsonPath().getString("status"));

    }


}
