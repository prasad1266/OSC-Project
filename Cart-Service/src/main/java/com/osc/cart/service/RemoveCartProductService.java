package com.osc.cart.service;

import com.cart.ProductQuantityRequest;

public interface RemoveCartProductService {
    void removeProductFromCart(ProductQuantityRequest request);
}