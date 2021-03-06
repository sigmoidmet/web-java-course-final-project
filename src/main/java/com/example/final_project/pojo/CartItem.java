package com.example.final_project.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CartItem {

    @EqualsAndHashCode.Include
    private Long id;

    private Long quantity;
}
