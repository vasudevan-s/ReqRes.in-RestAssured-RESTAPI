package in.reqres.suites;

import in.reqres.base.APIBase;
import in.reqres.misc.Common;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

/*
Created By: Vasudevan Sampath

 APITests.java has REST API tests using RestAssured framework
 */
public class APITests extends APIBase {

    @Test(priority = 0, description = "GET method with single user")
    public void getSpecificUserTest() {
        RestAssured
                .given()
                    .spec(Common.conformToRequestSpec())
                .when()
                    .get("/users")
                .then()
                    .spec(Common.conformToResponseSpec(200, "application/json"))
                 .body("data[1].id", Matchers.equalTo(2))
                  .body("data[1].email", Matchers.equalTo("janet.weaver@reqres.in"));
    }

    @Test(priority = 1, description = "GET method with multiple users")
    public void getFewFirstNamesTest() {
        RestAssured
                .given()
                    .spec(Common.conformToRequestSpec())
                .when()
                    .get("/users")
                .then()
                     .spec(Common.conformToResponseSpec(200, "application/json"))
                .body("data.first_name", Matchers.hasItems("Janet", "Eve", "Tracey"));
    }

    @Test(priority = 2, description = "POST method for registering a user")
    public void addNewUserPassingTest() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", "eve.holt@reqres.in");
        jsonObject.put("password", "pistol");

        RestAssured
                .given()
                    .spec(Common.conformToRequestSpec())
                    .body(jsonObject.toJSONString())
                .when()
                     .post("/register")
                .then()
                      .spec(Common.conformToResponseSpec(200, "application/json"))
                       .body("id", Matchers.equalTo(4))
                       .body("token", Matchers.equalTo("QpwL5tke4Pnpja7X4"));
    }

    @Test(priority = 3, description = "POST method for registering a user failure test")
    public void addNewUserFailingTest() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", "eve.holt@reqres.in");

        JSONObject responseObject = new JSONObject();
        responseObject.put("error", "Missing password");

        RestAssured
                .given()
                    .spec(Common.conformToRequestSpec())
                     .body(jsonObject.toJSONString())
                .when()
                    .post("/register")
                .then()
                     .spec(Common.conformToResponseSpec(400, "application/json"))
                     .extract().response().asString().equals(responseObject.toJSONString());
    }

    @Test(priority = 4, description = "POST method for adding a new user with more info")
    public void createUserTest() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "Joe Cameron");
        jsonObject.put("job", "AI Evangelist");

        JSONObject responseObject = new JSONObject();
        responseObject.put("name", "Joe Cameron");
        responseObject.put("job", "AI Evangelist");
        responseObject.put("id", "134");
        responseObject.put("createdAt", "2024-04-21T03:01:35.282Z");

        RestAssured
                .given()
                    .spec(Common.conformToRequestSpec())
                    .body(jsonObject.toJSONString())
                .when()
                     .post("/users")
                .then()
                    .spec(Common.conformToResponseSpec(201, "application/json"))
                    .extract().response().body().asString().equals(responseObject.toJSONString());
    }

    @Test(priority = 5, description = "PUT method for updating an existing user")
    public void updateUserPutTest() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "Joe Cameron");
        jsonObject.put("job", "AI Architect");

        JSONObject responseObject = new JSONObject();
        responseObject.put("name", "Joe Cameron");
        responseObject.put("job", "AI Architect");
        responseObject.put("updatedAt", "2024-04-21T03:15:07.054Z");

        RestAssured
                .given()
                     .spec(Common.conformToRequestSpec())
                     .body(jsonObject.toJSONString())
                .when()
                      .put("/users/2")
                .then()
                      .spec(Common.conformToResponseSpec(200, "application/json"))
                      .extract().response().body().asString().equals(responseObject.toJSONString());
    }

    @Test(priority = 6, description = "PATCH method for updating an existing user")
    public void updateUserPatchTest() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "Joe Cameron");
        jsonObject.put("job", "AI Architect");

        JSONObject responseObject = new JSONObject();
        responseObject.put("name", "Joe Cameron");
        responseObject.put("job", "AI Architect");
        responseObject.put("updatedAt", "2024-04-21T03:15:07.054Z");

        RestAssured
                .given()
                .spec(Common.conformToRequestSpec())
                .body(jsonObject.toJSONString())
                .when()
                .patch("/users/2")
                .then()
                .spec(Common.conformToResponseSpec(200, "application/json"))
                .extract().response().body().asString().equals(responseObject.toJSONString());
    }

    @Test(priority = 7, description = "DELETE method for deleting a user")
    public void deleteUserTest() {
        RestAssured
                .given()
                .spec(Common.conformToRequestSpec())
                .when()
                .delete("/users/2")
                .then()
                .spec(Common.conformToResponseSpec(204, "   "));
    }
}