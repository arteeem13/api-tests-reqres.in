package com.andreev.tests;

import com.andreev.pojoObjects.requests.CreateRequestBody;
import com.andreev.pojoObjects.responses.CreateResponseBody;
import com.andreev.specifications.BaseData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.andreev.filters.CustomLogFilter.customLogFilter;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Create user with method POST /api/users")
public class CreateUserTest extends BaseData {
    private final String name = "Artem";
    private final String job = "QA-engineer";

    CreateRequestBody request = new CreateRequestBody();

    @DisplayName("Checking status-code 201 and fields in response body")
    @Test
    void checkMethodPut() {
        request.setName(name);
        request.setJob(job);

        step("Calling method POST /api/users and checking response code 201", () -> {
            CreateResponseBody response = given()
                    .filter(customLogFilter().withCustomTemplates())
                    .contentType(JSON)
                    .body(request)
                    .baseUri(BaseData.BASE_URI)
                    .basePath("/api/users")
                    .when().log().method().log().uri().log().body().post()
                    .then()
                    .statusCode(201).log().status()
                    .log().body().extract().as(CreateResponseBody.class);

            step(String.format("Field 'name' from response equal '%s'", name), () ->
                    assertThat(response.getName()).isEqualTo(name));

            step(String.format("Field 'job' from response equal '%s'", job), () ->
                    assertThat(response.getJob()).isEqualTo(job));

            step("Field 'id' is not empty", () ->
                    assertThat(response.getId()).isNotEmpty());

            step("Field 'createdAt' is not empty", () ->
                    assertThat(response.getCreatedAt()).isNotEmpty());
        });
    }
}
