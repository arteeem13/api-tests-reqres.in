package com.andreev.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojoObjects.responses.GetListResourceResponseBody;

import static io.qameta.allure.Allure.attachment;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Метод GET /api/unknown")
public class GetListResourceTest {
    @DisplayName("Статус код 200 и проверка полей в теле ответа")
    @Test
    void checkMethodGetListResource() {
        step("Вызываем метод GET /api/unknown", () -> {
            GetListResourceResponseBody response = given()
                    .contentType(JSON)
                    .baseUri(Specifications.BASE_URI)
                    .basePath("/api/unknown")
                    .when().log().method().log().uri().log().body().get()
                    .then()
                    .statusCode(200).log().status()
                    .log().body().extract().as(GetListResourceResponseBody.class);

            step("Во всех блоках юзеров data есть непустые поля id, name, year, color, pantone_value", () -> {
                for (int i = 0; i < response.getData().size(); i++) {
                    assertThat(response.getData().get(i).getId()).isNotNull();
                    assertThat(response.getData().get(i).getName()).isNotNull();
                    assertThat(response.getData().get(i).getYear()).isNotNull();
                    assertThat(response.getData().get(i).getColor()).isNotNull();
                    assertThat(response.getData().get(i).getPantone_value()).isNotNull();
                }
            });
            step("Найден юзер с id 5, у которого имя 'tigerlily' и год '2004'", () -> {
                assertThat(response.getData().get(4).getName()).isEqualTo("tigerlily");
                assertThat(response.getData().get(4).getYear()).isEqualTo(2004);
            });
            step("Значение поля page = 1", () -> {
                assertThat(response.getPage()).isEqualTo(1);
            });
            step("Cсылка поддержки: 'https://reqres.in/#support-heading'", () -> {
                assertThat(response.getSupport().getUrl()).isEqualTo("https://reqres.in/#support-heading");
            });
            attachment("Tело ответа: ", response + "");
        });
    }
}