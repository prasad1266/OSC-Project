package com.osc.recentlyviewservice.serviceImpl;

import com.osc.avro.files.RecentViewHistory;
import com.osc.recentlyviewservice.mapper.RecentViewdProductsMapper;
import com.osc.recentlyviewservice.service.CheckRecentHistoryService;
import com.recentHistory.RecentlyViewedRequest;
import com.recentHistory.RecentlyViewedResponse;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class checkRecentHistoryServiceImpl implements CheckRecentHistoryService {

    public checkRecentHistoryServiceImpl(@Qualifier("RecentViewHistoryStore") ReadOnlyKeyValueStore<String, RecentViewHistory> recentViewdProductsStore) {
        this.recentViewdProductsStore = recentViewdProductsStore;
    }

    private ReadOnlyKeyValueStore<String, RecentViewHistory>  recentViewdProductsStore;

    @Override
    public RecentlyViewedResponse checkRecentHistorySattusinKtable(RecentlyViewedRequest recentlyViewedRequest) {
        RecentlyViewedResponse  recentViewHistoryresponse = null;
        RecentViewHistory recentViewHistory = recentViewdProductsStore.get(recentlyViewedRequest.getUserId());
      //  System.out.println("RecentViewHistoryEntity list For User "+recentViewHistory);
        if(recentViewHistory != null){
            recentViewHistoryresponse = RecentViewdProductsMapper.mapToRecentlyViewedResponse(recentViewHistory);
            return recentViewHistoryresponse;
        }
        return recentViewHistoryresponse;
    }
}
