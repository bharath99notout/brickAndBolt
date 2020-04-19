package com.example.brickAndBolt.service;

import com.example.brickAndBolt.model.Referrer;

import java.util.List;

public interface ReferrerService {

    public Referrer createReferrer(Referrer referrer);

    public boolean checkValidReferrerCode(String referrerCode);

    public boolean updateReferrerWallet(Referrer referee, String referralCode);

    public List<Referrer> getAllRefrees(Referrer referrer);

    public Double getReferrerReferralAmount(Referrer referrer);
}
