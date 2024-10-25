package com.osc.recentlyviewservice.service;

import com.recentHistory.RecentlyViewedRequest;
import com.recentHistory.RecentlyViewedResponse;


public interface CheckRecentHistoryService {
    RecentlyViewedResponse checkRecentHistorySattusinKtable(RecentlyViewedRequest recentlyViewedRequest);
}
