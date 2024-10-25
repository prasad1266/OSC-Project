package com.osc.dashboardservice.serviceImpl;

import com.osc.dashboardservice.dto.GetProductDetailsDto;
import com.osc.dashboardservice.mapper.RequestMapper;
import com.osc.dashboardservice.service.UpdateRecentHistory;
import com.recentHistory.RecentlyViewedServiceGrpc;
import com.recentHistory.UpdateRecentViewedRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UpdateRecentHistoryImpl implements UpdateRecentHistory {

    private RecentlyViewedServiceGrpc.RecentlyViewedServiceBlockingStub recentlyViewedServiceBlockingStub;

    public UpdateRecentHistoryImpl(@Qualifier("recentlyViewdServiceBlockingStub") RecentlyViewedServiceGrpc.RecentlyViewedServiceBlockingStub recentlyViewedServiceBlockingStub) {
        this.recentlyViewedServiceBlockingStub = recentlyViewedServiceBlockingStub;
    }

    @Override
    public void UpdateRecentHitoryList(GetProductDetailsDto getProductDetailsDto) {
        UpdateRecentViewedRequest request = RequestMapper.maptoUpdateRecentViewedRequest(getProductDetailsDto) ;
        recentlyViewedServiceBlockingStub.updateRecentlyViewedProducts(request);

    }
}
