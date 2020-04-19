package com.example.brickAndBolt.model;

import lombok.Data;

@Data
public class Referrer {

    private String name;

    private String mobileNumber;

    private String referrerCode;

    private String referrerLink;

    private Double referralAmount = 0.0;
}
