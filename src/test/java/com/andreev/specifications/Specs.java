package com.andreev.specifications;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.with;

public class Specs {

    public static RequestSpecification request = with()
            .baseUri("https://reqres.in/")
            .log().all()
            .contentType(ContentType.JSON);
}
