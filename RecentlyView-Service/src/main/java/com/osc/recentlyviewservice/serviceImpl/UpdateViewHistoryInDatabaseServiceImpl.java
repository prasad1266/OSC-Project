package com.osc.recentlyviewservice.serviceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.osc.avro.files.RecentViewHistory;
import com.osc.recentlyviewservice.entity.RecentViewHistoryEntity;
import com.osc.recentlyviewservice.repository.RecentViewHistoryRepository;
import com.osc.recentlyviewservice.service.UpdateViewHistoryInDatabaseService;
import com.recentHistory.RecentlyViewedRequest;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UpdateViewHistoryInDatabaseServiceImpl implements UpdateViewHistoryInDatabaseService {
    private ReadOnlyKeyValueStore<String, RecentViewHistory> recentViewdProductsStore;
private ObjectMapper objectMapper;
private RecentViewHistoryRepository recentViewHistoryRepository;

    public UpdateViewHistoryInDatabaseServiceImpl(ReadOnlyKeyValueStore<String, RecentViewHistory> recentViewdProductsStore,
                                                  ObjectMapper objectMapper,
                                                  RecentViewHistoryRepository recentViewHistoryRepository) {
        this.recentViewdProductsStore = recentViewdProductsStore;
        this.objectMapper = objectMapper;
        this.recentViewHistoryRepository = recentViewHistoryRepository;
    }

    @Override
    public void updateRecentViewHistory(RecentlyViewedRequest request) {
        String userId = request.getUserId();
        RecentViewHistory recentViewHistory = recentViewdProductsStore.get(userId);
    if(recentViewHistory!=null){
        List<String> productIds = new ArrayList<>();
        for (CharSequence id : recentViewHistory.getProductIds()) {
            productIds.add(id.toString());
        }

        try {
            String ids = objectMapper.writeValueAsString(productIds);
            System.out.println("ProductIds : "+ids);
            recentViewHistoryRepository.save(new RecentViewHistoryEntity(userId, ids));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        // Save the list directly as JSONB


        }
    }

}
