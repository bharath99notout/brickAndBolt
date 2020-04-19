package com.example.brickAndBolt.service.impl;

import com.example.brickAndBolt.model.Cart;
import com.example.brickAndBolt.model.PaynowResponse;
import com.example.brickAndBolt.model.Product;
import com.example.brickAndBolt.service.CartService;
import com.example.brickAndBolt.service.ReferrerService;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Component
public class CartServiceImpl implements CartService {

    @Autowired
    ReferrerService referrerService;

    @Value("${refereeDiscount}")
    private String refereeDiscount;

    @Override
    public Cart getCartDetails(Cart cart) {
        log.info("getCartDetails = "+cart);
        calculateTotalAmount(cart);
        calculateDiscount(cart);
        return cart;
    }

    @Override
    public PaynowResponse updateCartDetails(Cart cart) {
        PaynowResponse paynowResponse = new PaynowResponse();
        getCartDetails(cart);
        if (cart.getDiscountAmount() > 0 && referrerService.updateReferrerWallet(cart.getCustomer(), cart.getReferralCode())){
            paynowResponse.setHttpCode(200);
            paynowResponse.setMessage("Updated Wallet");
            return paynowResponse;
        }
        paynowResponse.setHttpCode(200);
        paynowResponse.setMessage("SUCCESS");
        return paynowResponse;
    }

    private void calculateTotalAmount(Cart cart){

        List<Product> products = cart.getProducts();
        Double totalAmount = 0.0;
        for (Product product: products) {
            totalAmount = totalAmount + product.getAmount();
        }
        cart.setTotalAmount(totalAmount);
        cart.setPayableAmount(totalAmount);
    }

    private void calculateDiscount(Cart cart){

        Double refereeDiscountDouble = 0.0;
        if (referrerService.checkValidReferrerCode(cart.getReferralCode())){
            if (!StringUtils.isEmpty(refereeDiscount)){
                refereeDiscountDouble = Double.parseDouble(refereeDiscount);
                log.info("discount value  = "+refereeDiscountDouble);
                cart.setDiscountAmount(refereeDiscountDouble);
                cart.setPayableAmount(cart.getTotalAmount() - refereeDiscountDouble);
                return;
            }
        }
        log.info("No discount  = "+refereeDiscountDouble);
        cart.setDiscountAmount(refereeDiscountDouble);
    }
}
