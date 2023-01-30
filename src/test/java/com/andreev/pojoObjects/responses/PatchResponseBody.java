package com.andreev.pojoObjects.responses;

import lombok.Data;

@Data
public class PatchResponseBody {
    private String name;
    private String job;
    private String updatedAt;
}
