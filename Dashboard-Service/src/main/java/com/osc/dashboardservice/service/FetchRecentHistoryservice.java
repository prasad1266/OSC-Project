package com.osc.dashboardservice.service;

import com.osc.dashboardservice.dto.DashboardDto;
import com.recentHistory.RecentlyViewedResponse;

public interface FetchRecentHistoryservice {
    RecentlyViewedResponse recentHistoryOfUser(DashboardDto dashboardDto);
}
