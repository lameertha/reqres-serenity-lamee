package com.req.res.testbase;

import io.restassured.RestAssured;
import org.junit.BeforeClass;

/* Created
 * by Lamee */public class TestBase {
    @BeforeClass
    public static void inIt() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }
}
