package com.andreev.pojoObjects.responses;

import lombok.Data;

@Data
public class CreateResponseBody {
    private String name;
    private String job;
    private String id;
    private String createdAt;
}
