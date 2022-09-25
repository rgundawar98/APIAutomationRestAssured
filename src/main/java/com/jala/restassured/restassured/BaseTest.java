package com.jala.restassured.restassured;

import org.junit.Test;


import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;
;


public class BaseTest {

    /**
     * properties is a file extension
     * for files mainly used in Java-related technologies to store the configurable parameters of an application
     * Object For accessing property file
     */
     protected static Properties prop = new Properties();
     protected static Logger log = Logger.getLogger("devpinoyLogger");

    @Test
    public void propertySetup() throws IOException {

        /**
         * Object For Reading the property file
         * Remember to change the path of reader object according to your local system
         */


        FileReader reader=new FileReader("D:\\JALAOffice\\Repos\\RestAssured\\api-automation-restassured\\src\\test\\resources\\config.properties");

        //Loading the  data from the InputStream object

        prop.load(reader);
        /* base URI that's used by REST assured when making requests */

    }


}




