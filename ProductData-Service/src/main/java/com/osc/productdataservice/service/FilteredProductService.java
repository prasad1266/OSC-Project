package com.osc.productdataservice.service;

import com.Product.FilterProductRequest;
import com.Product.FilterProductResponse;

public interface FilteredProductService {
     FilterProductResponse getFilteredProducts(FilterProductRequest request);
}
