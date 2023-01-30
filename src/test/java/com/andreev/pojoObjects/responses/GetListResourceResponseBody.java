package com.andreev.pojoObjects.responses;

import lombok.Data;

import java.util.List;

@Data
public class GetListResourceResponseBody {
    private int page;
    private int per_page;
    private int total;
    private int total_pages;
    private List<DataItem> data;
    private Support support;

    @Data
    public static class DataItem {
        private int id;
        private String name;
        private int year;
        private String color;
        private String pantone_value;
    }

    @Data
    public static class Support {
        private String url;
        private String text;
    }
}
