package com.example.final_project.pojo;

import lombok.Data;

@Data
public class QueryParam {

    private final String name;

    private final String value;


    public static QueryParam param(String name, String value) {
        return new QueryParam(name, value);
    }
}
