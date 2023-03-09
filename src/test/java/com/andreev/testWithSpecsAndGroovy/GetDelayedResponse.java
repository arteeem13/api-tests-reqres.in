package com.andreev.testWithSpecsAndGroovy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.andreev.specifications.Specs.request;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;

@DisplayName("Checking method GET /users?delay=3 with Groovy")
public class GetDelayedResponse {

    @DisplayName("Checking status code 200 and field 'email' contains '@reqres.in'")
    @Test
    public void checkEmailFromResponseBody() {
        // @formatter:off
        given()
                .spec(request)
                .when()
                .get("/users?delay=3")
                .then()
                .log().body()
                .body("data.findAll{it.email =~/.*?@reqres.in/}.email.flatten()",
                        hasItem("eve.holt@reqres.in"));
        // @formatter:on
    }
}
