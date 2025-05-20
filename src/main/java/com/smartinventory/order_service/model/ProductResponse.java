package com.smartinventory.order_service.model;

import lombok.Data;

@Data
public class ProductResponse {
    private String code;
    private String name;
    private Double price;
}
