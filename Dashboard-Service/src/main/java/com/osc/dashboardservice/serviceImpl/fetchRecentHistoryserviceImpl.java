package com.osc.dashboardservice.serviceImpl;

import com.osc.dashboardservice.dto.DashboardDto;
import com.osc.dashboardservice.mapper.RecentViewMapper;
import com.osc.dashboardservice.service.FetchRecentHistoryservice;
import com.recentHistory.RecentlyViewedRequest;
import com.recentHistory.RecentlyViewedResponse;
import com.recentHistory.RecentlyViewedServiceGrpc;
import org.springframework.stereotype.Service;

@Service
public class fetchRecentHistoryserviceImpl implements FetchRecentHistoryservice
{
private RecentlyViewedServiceGrpc.RecentlyViewedServiceBlockingStub recentlyViewedServiceBlockingStub;

    public fetchRecentHistoryserviceImpl(RecentlyViewedServiceGrpc.RecentlyViewedServiceBlockingStub recentlyViewedServiceBlockingStub) {
        this.recentlyViewedServiceBlockingStub = recentlyViewedServiceBlockingStub;
    }

    @Override
    public RecentlyViewedResponse recentHistoryOfUser(DashboardDto dashboardDto) {

        RecentlyViewedRequest recentlyViewedRequest = RecentViewMapper.maptoRecentViewMapper(dashboardDto);
        RecentlyViewedResponse recentlyViewedResponse =  recentlyViewedServiceBlockingStub.fetchRecentlyViewedHistory(recentlyViewedRequest);
        return recentlyViewedResponse;
    }
}
