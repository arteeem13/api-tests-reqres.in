package com.andreev.tests;

import com.andreev.pojoObjects.responses.GetListResourceResponseBody;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.attachment;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Удаление юзера методом DELETE /api/users/2")
public class DeleteUserTest {
    @DisplayName("Статус код 204 и пустое тело ответа")
    @Test
    void checkMethodGetListResource() {
        step("Вызываем метод DELETE /api/users/2 и проверяем код ответа 204", () -> {
            Response response = given()
                    .baseUri(Specifications.BASE_URI)
                    .basePath("/api/users/2")
                    .when().log().method().log().uri().log().body().delete()
                    .then()
                    .statusCode(204).log().status()
                    .log().body().extract().response();

            step("Тело ответа пустое", () -> {
                assertThat(response.asString()).isEmpty();
            });
        });
    }
}