package com.example.brickAndBolt.service;

import com.example.brickAndBolt.model.Cart;
import com.example.brickAndBolt.model.PaynowResponse;

public interface CartService {

    public Cart getCartDetails(Cart cart);

    public PaynowResponse updateCartDetails(Cart cart);
}
