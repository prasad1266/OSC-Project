package com.osc.recentlyviewservice.service;

import com.recentHistory.RecentlyViewedResponse;
import com.recentHistory.UpdateRecentViewedRequest;

public interface UpdateRecentlyViewService {

    void updateRecentViewdProductsList(UpdateRecentViewedRequest request);
}
