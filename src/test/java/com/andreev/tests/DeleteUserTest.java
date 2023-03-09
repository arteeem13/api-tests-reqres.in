package com.andreev.tests;

import com.andreev.specifications.BaseData;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.andreev.filters.CustomLogFilter.customLogFilter;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Deleting user with method DELETE /api/users/2")
public class DeleteUserTest extends BaseData {
    @DisplayName("Checking status-code 204 and empty response body")
    @Test
    void checkMethodGetListResource() {
        step("Calling method DELETE /api/users/2 and checking response code 204", () -> {
            Response response = given()
                    .filter(customLogFilter().withCustomTemplates())
                    .baseUri(BaseData.BASE_URI)
                    .basePath("/api/users/2")
                    .when().log().method().log().uri().log().body().delete()
                    .then()
                    .statusCode(204).log().status()
                    .log().body().extract().response();

            step("Response is empty", () ->
                    assertThat(response.asString()).isEmpty());
        });
    }
}