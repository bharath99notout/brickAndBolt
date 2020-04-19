package com.example.brickAndBolt.model;

import lombok.Data;

import java.util.List;

@Data
public class ReferrerDetails {

    private String name;

    private String mobileNumber;

    private List<Referrer> referrerList;

    private Double referralAmount;

}

