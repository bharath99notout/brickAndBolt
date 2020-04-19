package com.example.brickAndBolt.service.impl;

import com.example.brickAndBolt.model.Referrer;
import com.example.brickAndBolt.service.ReferrerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class ReferrerServiceImpl implements ReferrerService {

    private static final String domainName = "www.brickAndBot.com/";

    //Assuming mobile number will be unique
    public static ConcurrentHashMap<String,Referrer> referrerMap = new ConcurrentHashMap<>();

    //Keeping all the referrer code in map # Parameters referrer code as key and referrername +  mobilenumber as value
    public static ConcurrentHashMap<String,String> referrerCodeMap = new ConcurrentHashMap<>();

    //key is ReferralName and Mobile numaber and values all refrees
    public static ConcurrentHashMap<String,List<Referrer>> referreListMap = new ConcurrentHashMap<>();

    @Value("${referrerDiscount}")
    private String referrerDiscount;

    @Override
    public Referrer createReferrer(Referrer referrer) {

        String key = referrer.getName()+referrer.getMobileNumber();
        if (referrerMap.containsKey(key)){
            return referrerMap.get(key);
        }else {
            addReferrer(referrer);
        }
        return referrer;
    }

    @Override
    public boolean checkValidReferrerCode(String referrerCode) {

        if (StringUtils.isEmpty(referrerCode)){
            return false;
        }
        if (referrerCodeMap.containsKey(referrerCode)){
            log.info("referrer code exists = "+referrerCode);
            return true;
        }
        return false;
    }


    @Override
    public boolean updateReferrerWallet(Referrer referee, String referralCode) {
        if (!StringUtils.isEmpty(referrerDiscount) && referrerCodeMap.containsKey(referralCode)) {
            String referrerMapKey = referrerCodeMap.get(referralCode);
            Double refereeDiscountDouble = Double.valueOf(referrerDiscount);
            if (referrerMap.containsKey(referrerMapKey)) {

                Referrer referrer = referrerMap.get(referrerMapKey);
                referrer.setReferralAmount(referrer.getReferralAmount() + refereeDiscountDouble);
                return addRefreeToListMap(referrerMapKey, referee);
            }
            return false;
        }
        return false;
    }

    @Override
    public List<Referrer> getAllRefrees(Referrer referrer) {

        String key = referrer.getName() + referrer.getMobileNumber();
        if (referreListMap.containsKey(key)){
            return referreListMap.get(key);
        }
        return null;
    }

    @Override
    public Double getReferrerReferralAmount(Referrer referrer) {
        String key = referrer.getName() + referrer.getMobileNumber();
        if (referrerMap.containsKey(key)){
            Referrer referrerInMap = referrerMap.get(key);
            return referrerInMap.getReferralAmount();
        }
        return 0.0;
    }

    private boolean addRefreeToListMap(String referrerMapKey, Referrer referee){

        List<Referrer> referrerList = new LinkedList<>();
        if (referreListMap.containsKey(referrerMapKey)) {
            referrerList = referreListMap.get(referrerMapKey);
            if (referrerList == null || referrerList.size() == 0) {
                referrerList.add(referee);
            } else {
                referrerList.add(referee);
            }
        } else {
            referrerList.add(referee);
        }
        referreListMap.put(referrerMapKey, referrerList);
        return true;
    }

    private String generateUniqueLink (Referrer referrer){

        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new SecureRandom();
        for (int i = 0; i < 4; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String uniqueLink = referrer.getMobileNumber() + "" + sb.toString();
        log.info("uniqueLink = " + uniqueLink);
        return uniqueLink;
    }

    private void addReferrer (Referrer referrer){
        Boolean isFound = false;
        String uniqueLink = null;
        while (!isFound) {
            uniqueLink = generateUniqueLink(referrer);
            if (!referrerMap.containsKey(uniqueLink)) {
                isFound = true;
            }
        }
        referrer.setReferrerCode(uniqueLink);
        referrer.setReferrerLink(domainName + uniqueLink);
        String key = referrer.getName() + referrer.getMobileNumber();
        referrerCodeMap.put(uniqueLink, key);
        referrerMap.put(key, referrer);
    }
}
