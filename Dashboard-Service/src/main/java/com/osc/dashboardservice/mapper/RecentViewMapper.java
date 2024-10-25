package com.osc.dashboardservice.mapper;

import com.Product.DashboardRequest;
import com.osc.dashboardservice.dto.DashboardDto;
import com.recentHistory.RecentlyViewedRequest;
import com.recentHistory.RecentlyViewedResponse;

public class RecentViewMapper {

    public static RecentlyViewedRequest maptoRecentViewMapper(DashboardDto dashboardDto) {
       return RecentlyViewedRequest.newBuilder().setUserId(dashboardDto.getUserId()).build();
    }

    public static DashboardRequest maptoDashboardRequest(RecentlyViewedResponse response) {
        DashboardRequest.Builder dashboardRequestBuilder = DashboardRequest.newBuilder();

        dashboardRequestBuilder.addAllProductId(response.getProductIdList());
        return dashboardRequestBuilder.build();
    }
      //  DashboardRequest.newBuilder().addProductId(response.getProductId())

}
