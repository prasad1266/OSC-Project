package com.osc.dashboardservice.service;

import com.osc.dashboardservice.dto.ApiResponse;
import com.osc.dashboardservice.dto.GetProductDetailsDto;
import org.springframework.http.ResponseEntity;

public interface ProductDetailsService {


    ResponseEntity<ApiResponse> getProductDetails(GetProductDetailsDto getProductDetailsDto);
}