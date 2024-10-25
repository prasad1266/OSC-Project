package com.osc.productdataservice.service;

import com.Product.GetCartProductDetailsRequest;
import com.Product.GetCartProductDetailsResponse;

public interface GetCartProductDetailsService {
    GetCartProductDetailsResponse getCartProducts(GetCartProductDetailsRequest request);
}
