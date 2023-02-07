package com.andreev.tests;

import com.andreev.specifications.Specifications;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.andreev.filters.CustomLogFilter.customLogFilter;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Удаление юзера методом DELETE /api/users/2")
public class DeleteUserTest {
    @DisplayName("Статус код 204 и пустое тело ответа")
    @Test
    void checkMethodGetListResource() {
        step("Вызываем метод DELETE /api/users/2 и проверяем код ответа 204", () -> {
            Response response = given()
                    .filter(customLogFilter().withCustomTemplates())
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