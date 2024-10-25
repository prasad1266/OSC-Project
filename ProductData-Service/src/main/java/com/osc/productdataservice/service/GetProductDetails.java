package com.osc.productdataservice.service;

import com.Product.ProductDataRequest;
import com.Product.ProductDataResponse;

public interface GetProductDetails {

    ProductDataResponse getDetails(ProductDataRequest request);
}
