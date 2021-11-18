package com.example.final_project.service;

import com.example.final_project.pojo.QueryParam;

public interface UrlProcessor {

    String processRelativeUrl(String relativeUrl, QueryParam... queryParams);
}
