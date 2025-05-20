package com.smartinventory.order_service.model;

import lombok.Data;

@Data
public class OrderRequest {

    private String productCode;
    private Integer quantity;


}
