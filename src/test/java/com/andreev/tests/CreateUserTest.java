package com.andreev.tests;

import com.andreev.pojoObjects.requests.CreateRequestBody;
import com.andreev.pojoObjects.responses.CreateResponseBody;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.attachment;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Создание юзера методом POST /api/users")
public class CreateUserTest {
    private final String name = "Artem";
    private final String job = "QA-engineer";

    CreateRequestBody request = new CreateRequestBody();

    @DisplayName("Статус код 201 и проверка полей в теле ответа")
    @Test
    void checkMethodPut() {
        request.setName(name);
        request.setJob(job);

        step("Вызываем метод POST /api/users и проверяем код ответа 201", () -> {
            CreateResponseBody response = given()
                    .contentType(JSON)
                    .body(request)
                    .baseUri(Specifications.BASE_URI)
                    .basePath("/api/users")
                    .when().log().method().log().uri().log().body().post()
                    .then()
                    .statusCode(201).log().status()
                    .log().body().extract().as(CreateResponseBody.class);

            step("В теле ответа есть поле 'name' со значением '" + name + "'", () -> {
                assertThat(response.getName()).isEqualTo(name);
            });
            step("В теле ответа есть поле 'job' со значением '" + job + "'", () -> {
                assertThat(response.getJob()).isEqualTo(job);
            });
            step("В теле ответа есть поле 'id', которое не пустое", () -> {
                assertThat(response.getId()).isNotEmpty();
            });
            step("В теле ответа есть поле 'createdAt', которое не пустое", () -> {
                assertThat(response.getCreatedAt()).isNotEmpty();
            });
            attachment("Tело ответа: ", response + "");
        });
    }
}
