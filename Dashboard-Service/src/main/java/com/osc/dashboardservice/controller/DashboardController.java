package com.osc.dashboardservice.controller;

import com.Product.DashboardRequest;
import com.osc.dashboardservice.dto.ApiResponse;
import com.osc.dashboardservice.dto.DashboardDto;
import com.osc.dashboardservice.mapper.RecentViewMapper;
import com.osc.dashboardservice.service.FetchProductsDataservice;
import com.osc.dashboardservice.service.FetchRecentHistoryservice;
import com.recentHistory.RecentlyViewedResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
public class DashboardController {

    private FetchRecentHistoryservice fetchRecentHistoryservice;
    private FetchProductsDataservice fetchProductsDataservice;

    public DashboardController(FetchRecentHistoryservice fetchRecentHistoryservice,
                               FetchProductsDataservice fetchProductsDataservice) {
        this.fetchRecentHistoryservice = fetchRecentHistoryservice;
        this.fetchProductsDataservice = fetchProductsDataservice;
    }

    @PostMapping("/user/dashBoard")
    public ResponseEntity<ApiResponse> getRecenHistory(@RequestBody DashboardDto dashboardDto){

        RecentlyViewedResponse recentlyViewedResponse = fetchRecentHistoryservice.recentHistoryOfUser(dashboardDto);
       DashboardRequest dashboardRequest = RecentViewMapper.maptoDashboardRequest(recentlyViewedResponse);
       ResponseEntity<ApiResponse> apiResponse = fetchProductsDataservice.getUserProductData(dashboardRequest);

        return apiResponse;
    }
}
