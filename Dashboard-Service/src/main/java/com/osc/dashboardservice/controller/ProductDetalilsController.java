package com.osc.dashboardservice.controller;

import com.osc.dashboardservice.dto.ApiResponse;
import com.osc.dashboardservice.dto.GetProductDetailsDto;
import com.osc.dashboardservice.service.ProductDetailsService;
import com.osc.dashboardservice.service.UpdateRecentHistory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
public class ProductDetalilsController {

    private ProductDetailsService productDetailsService;
    private UpdateRecentHistory updateRecentHistory;

    public ProductDetalilsController(ProductDetailsService productDetailsService, UpdateRecentHistory updateRecentHistory) {
        this.productDetailsService = productDetailsService;
        this.updateRecentHistory = updateRecentHistory;
    }

    @PostMapping("/product/details")
    public ResponseEntity<ApiResponse> getProductDetails(@RequestBody GetProductDetailsDto getProductDetailsDto){
        //getting product Details From ProductData Service
        ResponseEntity<ApiResponse> response = productDetailsService.getProductDetails(getProductDetailsDto);
        //Updating RecentViewHistory in RecentViewHistory Service
        updateRecentHistory.UpdateRecentHitoryList(getProductDetailsDto);

        return response;

    }
}
