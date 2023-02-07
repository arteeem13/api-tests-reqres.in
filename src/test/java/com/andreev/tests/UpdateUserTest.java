package com.andreev.tests;

import com.andreev.pojoObjects.requests.UpdateRequestBody;
import com.andreev.pojoObjects.responses.UpdateResponseBody;
import com.andreev.specifications.Specifications;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.andreev.filters.CustomLogFilter.customLogFilter;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Обновление юзера методом PUT /api/users/{id}")
public class UpdateUserTest {
    private final String name = "Artem";
    private final String job = "QC-engineer";

    UpdateRequestBody request = new UpdateRequestBody();

    @DisplayName("Статус код 200 и проверка полей в теле ответа")
    @Test
    void checkMethodPut() {
        request.setName(name);
        request.setJob(job);

        step("Вызываем метод PUT /api/users/2 и проверяем код ответа 200", () -> {
            UpdateResponseBody response = given()
                    .filter(customLogFilter().withCustomTemplates())
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
        });
    }
}
