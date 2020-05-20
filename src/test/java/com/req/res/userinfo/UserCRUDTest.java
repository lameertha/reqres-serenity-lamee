package com.req.res.userinfo;

import com.req.res.model.UserPojo;
import com.req.res.testbase.TestBase;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static com.req.res.utils.TestUtils.getRandomString;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;

/* Created
 * by Lamee */
@RunWith(SerenityRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserCRUDTest extends TestBase {
    String name = "lamee" + getRandomString(2);
    String job = "Software Engineering"+getRandomString(2);
    String id;



    @Title("This test will create a new user record and verify its generated")
    @Test
    public void test001(){

        UserPojo userPojo = new UserPojo();

        userPojo.setName(name);
        userPojo.setJob(job);


        SerenityRest.rest()
                .given()
                .log().all()
                .contentType(ContentType.JSON)
                .when()
                .body(userPojo)
                .post("/users")
                .then()
                .statusCode(201).log().body()
                .body("name",equalTo(name));

    }
    @Title("This test will get user from the list")
    @Test

    public void test002(){
        UserPojo userPojo = new UserPojo();

        userPojo.setName(name= "Michael");

        SerenityRest.rest()
                .given()
                .when()
                .get("/users?page=2")
                .then().statusCode(200)
                .log().body()
                .body("data.first_name",hasItems(name));


    }


    @Title("This test will update single user and verify its updated")
    @Test
    public void test003() {

        UserPojo userPojo = new UserPojo();
        userPojo.setName(name = name+"_new");
        userPojo.setJob(job= job+"_Changed");

        SerenityRest.rest().given()
                .header("Content-Type","application/json")
                .when()
                .body(userPojo)
                .put("/users/5")
                .then().statusCode(200)
                .log().body()
                .body("name",equalTo(name))
                .body("job",equalTo(job));


    }
    @Title("This test will delete single user ")
    @Test
    public void test004(){
        SerenityRest.rest().given()
                .when()
                .delete("/users/2")
                .then()
                .statusCode(204)
                .log().ifValidationFails();

    }
}



