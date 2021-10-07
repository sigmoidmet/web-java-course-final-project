package com.example.final_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class WebJavaCourseFinalProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebJavaCourseFinalProjectApplication.class, args);
    }

}
