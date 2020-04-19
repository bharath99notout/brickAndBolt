package com.example.brickAndBolt.model;

import lombok.Data;

import java.util.List;

@Data
public class Cart {

    private Referrer customer;

    private List<Product> products;

    private Double totalAmount;

    private Double discountAmount;

    private Double payableAmount;

    private String referralCode;
}
