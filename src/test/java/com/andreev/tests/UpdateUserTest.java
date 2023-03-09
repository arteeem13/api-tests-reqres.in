package com.andreev.tests;

import com.andreev.pojoObjects.requests.UpdateRequestBody;
import com.andreev.pojoObjects.responses.UpdateResponseBody;
import com.andreev.specifications.BaseData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.andreev.filters.CustomLogFilter.customLogFilter;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Updating users with method PUT /api/users/{id}")
public class UpdateUserTest extends BaseData {
    private final String name = "Artem";
    private final String job = "QC-engineer";

    UpdateRequestBody request = new UpdateRequestBody();

    @DisplayName("Checking status code 200 and fields from response body")
    @Test
    void checkMethodPut() {
        request.setName(name);
        request.setJob(job);

        step("Calling method PUT /api/users/2 and check status code 200", () -> {
            UpdateResponseBody response = given()
                    .filter(customLogFilter().withCustomTemplates())
                    .contentType(JSON)
                    .body(request)
                    .baseUri(BaseData.BASE_URI)
                    .basePath("/api/users/2")
                    .when().log().method().log().uri().log().body().put()
                    .then()
                    .statusCode(200).log().status()
                    .log().body().extract().as(UpdateResponseBody.class);

            step(String.format("Field 'name' from response body equal to '%s'", name), () ->
                    assertThat(response.getName()).isEqualTo(name));

            step(String.format("Field 'job' from response body equal to '%s'", job), () ->
                    assertThat(response.getJob()).isEqualTo(job));

            step("Field 'updatedAt' from response body is not empty", () ->
                    assertThat(response.getUpdatedAt()).isNotEmpty());
        });
    }
}
