package com.andreev.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojoObjects.requests.UpdateRequestBody;
import pojoObjects.responses.UpdateResponseBody;

import static io.qameta.allure.Allure.attachment;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Метод PUT /api/users/{id}")
public class PutUpdateTest {
    private final String name = "morpheus";
    private final String job = "zion resident";

    UpdateRequestBody request = new UpdateRequestBody();

    @DisplayName("Статус код 200 и проверка полей в теле ответа")
    @Test
    void checkMethodPut() {
        request.setName(name);
        request.setJob(job);

        step("Вызываем метод PUT /api/users/2", () -> {
            UpdateResponseBody response = given()
                    .contentType(JSON)
                    .body(request)
                    .baseUri(Specifications.BASE_URI)
                    .basePath("/api/users/2")
                    .when().log().method().log().uri().log().body().put()
                    .then()
                    .statusCode(200).log().status()
                    .log().body().extract().as(UpdateResponseBody.class);

            step("В теле ответа есть поле 'name' со значением '" + name + "'", () -> {
                assertThat(response.getName()).isEqualTo(name);
            });
            step("В теле ответа есть поле 'job' со значением '" + job + "'", () -> {
                assertThat(response.getJob()).isEqualTo(job);
            });
            step("В теле ответа есть поле 'updatedAt', которое не пустое", () -> {
                assertThat(response.getUpdatedAt()).isNotEmpty();
            });
            attachment("Tело ответа: ", response + "");
        });
    }
}
