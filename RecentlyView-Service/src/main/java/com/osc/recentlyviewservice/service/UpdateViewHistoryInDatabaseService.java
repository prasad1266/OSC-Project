package com.osc.recentlyviewservice.service;

import com.recentHistory.RecentlyViewedRequest;

public interface UpdateViewHistoryInDatabaseService {
    void updateRecentViewHistory(RecentlyViewedRequest request);
}
