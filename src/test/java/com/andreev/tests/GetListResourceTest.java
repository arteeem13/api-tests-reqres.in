package com.andreev.tests;

import com.andreev.pojoObjects.responses.GetListResourceResponseBody;
import com.andreev.specifications.BaseData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.andreev.filters.CustomLogFilter.customLogFilter;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Getting user lists with method GET /api/unknown")
public class GetListResourceTest extends BaseData {
    @DisplayName("Checking status code 200 and fields from response body")
    @Test
    void checkMethodGetListResource() {
        step("Calling method GET /api/unknown and checking response code 200", () -> {
            GetListResourceResponseBody response = given()
                    .filter(customLogFilter().withCustomTemplates())
                    .contentType(JSON)
                    .baseUri(BaseData.BASE_URI)
                    .basePath("/api/unknown")
                    .when().log().method().log().uri().log().body().get()
                    .then()
                    .statusCode(200).log().status()
                    .log().body().extract().as(GetListResourceResponseBody.class);

            step("All user blocks 'data' contains not null fields id, name, year, color, pantone_value", () -> {
                response.getData().forEach(e -> assertThat(e.getId()).isNotNull());
                response.getData().forEach(e -> assertThat(e.getName()).isNotNull());
                response.getData().forEach(e -> assertThat(e.getYear()).isNotNull());
                response.getData().forEach(e -> assertThat(e.getColor()).isNotNull());
                response.getData().forEach(e -> assertThat(e.getPantoneValue()).isNotNull());
            });

            step("Find user with id = 5 with name equal to 'tigerlily' and year  equal to '2004'", () -> {
                assertThat(response.getData().get(4).getName()).isEqualTo("tigerlily");
                assertThat(response.getData().get(4).getYear()).isEqualTo(2004);
            });

            step("Field page equal to 1", () ->
                    assertThat(response.getPage()).isEqualTo(1));

            step("Supports link is equal to 'https://reqres.in/#support-heading'", () ->
                    assertThat(response.getSupport().getUrl()).isEqualTo("https://reqres.in/#support-heading"));
        });
    }
}