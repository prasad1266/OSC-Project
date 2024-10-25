package com.osc.cart.service;

import com.cart.CartDetailsRequest;
import com.cart.CartDetailsResponse;

public interface FetchCartDetailsServic {
    CartDetailsResponse getCartDetails(CartDetailsRequest request);
}
