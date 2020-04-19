package com.example.brickAndBolt.controller;

import com.example.brickAndBolt.model.Referrer;
import com.example.brickAndBolt.model.ReferrerDetails;
import com.example.brickAndBolt.service.ReferrerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/referrer")
public class ReferrerLandingController {

    @Autowired
    ReferrerService referrerService;

    @RequestMapping(value = "/" , method = RequestMethod.POST)
    public ResponseEntity<Referrer> createReferrer(@RequestBody Referrer referrer){

        if (StringUtils.isEmpty(referrer.getName()) || StringUtils.isEmpty(referrer.getMobileNumber())){
            log.info("Referrer name or Referrer Mobile number is empty");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Referrer>(referrerService.createReferrer(referrer),HttpStatus.OK);
    }

    @RequestMapping(value = "/getReferralDetails" , method = RequestMethod.GET)
    public ResponseEntity<ReferrerDetails> getAllRefrees(@RequestBody Referrer referrer){

        if (StringUtils.isEmpty(referrer.getName()) || StringUtils.isEmpty(referrer.getMobileNumber())){
            log.info("Referrer name or Referrer Mobile number is empty");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ReferrerDetails referrerDetails = new ReferrerDetails();
        referrerDetails.setName(referrer.getName());
        referrerDetails.setMobileNumber(referrer.getMobileNumber());
        referrerDetails.setReferrerList(referrerService.getAllRefrees(referrer));
        referrerDetails.setReferralAmount(referrerService.getReferrerReferralAmount(referrer));
        return new ResponseEntity<ReferrerDetails>(referrerDetails,HttpStatus.OK);
    }

}
