package com.example.brickAndBolt.controller;

import com.example.brickAndBolt.model.Cart;
import com.example.brickAndBolt.model.PaynowResponse;
import com.example.brickAndBolt.model.Referee;
import com.example.brickAndBolt.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/referee")
public class CartController {


    @Autowired
    CartService cartService;

    @RequestMapping(value = "/cart" , method = RequestMethod.POST)
    public ResponseEntity<Cart> getCartDetails(@RequestBody Cart cart){

        if (StringUtils.isEmpty(cart.getCustomer())){
            log.info("Cart customer name is empty");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (cart.getProducts() == null || cart.getProducts().size() == 0){
            log.info("Cart products is empty");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Cart>(cartService.getCartDetails(cart),HttpStatus.OK);
    }

    @RequestMapping(value = "/paynow" , method = RequestMethod.POST)
    public ResponseEntity<PaynowResponse> createOrderAndPay(@RequestBody Cart cart){

        if (StringUtils.isEmpty(cart.getCustomer())){
            log.info("Cart customer name is empty");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (cart.getProducts() == null || cart.getProducts().size() == 0){
            log.info("Cart products is empty");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<PaynowResponse>(cartService.updateCartDetails(cart),HttpStatus.OK);
    }
}
