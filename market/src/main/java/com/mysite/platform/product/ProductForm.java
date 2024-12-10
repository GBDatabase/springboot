package com.mysite.platform.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductForm {
    private String code;
    private String name;
    private Integer quantity;
    private Double price;
    private String vendor;
    private String category;
}
