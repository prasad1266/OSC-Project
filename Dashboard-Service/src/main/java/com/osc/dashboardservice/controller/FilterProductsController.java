package com.osc.dashboardservice.controller;

import com.osc.dashboardservice.dto.ApiResponse;
import com.osc.dashboardservice.dto.FilterProductRequestDto;
import com.osc.dashboardservice.service.FilteredProductsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
public class FilterProductsController {
    private FilteredProductsService filteredProductsService;

    public FilterProductsController(FilteredProductsService filteredProductsService) {
        this.filteredProductsService = filteredProductsService;
    }

    @PostMapping("/filter/product")
    public ResponseEntity<ApiResponse> filterProductDetails(@RequestBody FilterProductRequestDto filterProductRequestDto){
        ResponseEntity<ApiResponse> response = filteredProductsService.getFilterdProducts(filterProductRequestDto);
        return response;
    }
}

