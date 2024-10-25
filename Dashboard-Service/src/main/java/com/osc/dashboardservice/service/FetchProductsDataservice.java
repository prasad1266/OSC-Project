package com.osc.dashboardservice.service;

import com.Product.DashboardRequest;
import com.osc.dashboardservice.dto.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface FetchProductsDataservice {

    ResponseEntity<ApiResponse> getUserProductData(DashboardRequest request);
}
