package com.osc.dashboardservice.service;

import com.osc.dashboardservice.dto.ApiResponse;
import com.osc.dashboardservice.dto.FilterProductRequestDto;
import org.springframework.http.ResponseEntity;

public interface FilteredProductsService {
    ResponseEntity<ApiResponse> getFilterdProducts(FilterProductRequestDto filterProductRequestDto);
}
