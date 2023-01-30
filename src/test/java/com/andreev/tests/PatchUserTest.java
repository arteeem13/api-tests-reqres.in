package com.andreev.tests;

import com.andreev.pojoObjects.requests.PatchRequestBody;
import com.andreev.pojoObjects.requests.UpdateRequestBody;
import com.andreev.pojoObjects.responses.PatchResponseBody;
import com.andreev.pojoObjects.responses.UpdateResponseBody;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.attachment;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Обновление имени юзера методом PATCH /api/users/{id}")
public class PatchUserTest {
    private final String name = "Artem";
    private final String job = "testing";

    PatchRequestBody request = new PatchRequestBody();

    @DisplayName("Статус код 200 и проверка полей в теле ответа")
    @Test
    void checkMethodPut() {
        request.setName(name);
        request.setJob(job);

        step("Вызываем метод PATCH /api/users/2 и проверяем код ответа 200", () -> {
            PatchResponseBody response = given()
                    .contentType(JSON)
                    .body(request)
                    .baseUri(Specifications.BASE_URI)
                    .basePath("/api/users/2")
                    .when().log().method().log().uri().log().body().patch()
                    .then()
                    .statusCode(200).log().status()
                    .log().body().extract().as(PatchResponseBody.class);

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
