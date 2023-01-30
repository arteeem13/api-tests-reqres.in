package com.andreev.pojoObjects.requests;

import lombok.Data;

@Data
public class PatchRequestBody {
    private String name;
    private String job;
}
