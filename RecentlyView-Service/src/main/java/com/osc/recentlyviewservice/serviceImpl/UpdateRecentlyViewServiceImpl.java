package com.osc.recentlyviewservice.serviceImpl;

import com.osc.avro.files.RecentViewHistory;
import com.osc.recentlyviewservice.Constants.AppConstants;
import com.osc.recentlyviewservice.service.UpdateRecentlyViewService;
import com.recentHistory.UpdateRecentViewedRequest;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class UpdateRecentlyViewServiceImpl implements UpdateRecentlyViewService {
    private KafkaTemplate<String, RecentViewHistory> kafkaTemplate;

    private ReadOnlyKeyValueStore<String, RecentViewHistory> recentViewdProductsStore;

    public UpdateRecentlyViewServiceImpl(@Qualifier("kafkaTemaplate") KafkaTemplate<String, RecentViewHistory> kafkaTemplate, @Qualifier("RecentViewHistoryStore") ReadOnlyKeyValueStore<String, RecentViewHistory> recentViewdProductsStore) {
        this.kafkaTemplate = kafkaTemplate;
        this.recentViewdProductsStore = recentViewdProductsStore;
    }

    @Override
    public void updateRecentViewdProductsList(UpdateRecentViewedRequest request) {
        CharSequence productId = request.getProductId();
        String userId = request.getUserId().toString();

        RecentViewHistory recentViewHistory = recentViewdProductsStore.get(userId);

        List<CharSequence> recetViewdProductsList = new LinkedList<>();

        System.out.println("recentViewHistory of User is "+ recentViewHistory);

        if (recentViewHistory == null) {
           // System.out.println("recentViewHistory of User is "+ recentViewHistory);
            recetViewdProductsList.add(0,productId);

            recentViewHistory = RecentViewHistory.newBuilder()
                    .setProductIds(recetViewdProductsList)
                    .build();
            kafkaTemplate.send(AppConstants.RECENTLY_VIEWD_PRODUCT_Topic,userId,recentViewHistory);


        }else {
            recetViewdProductsList = recentViewHistory.getProductIds();
           // System.out.println("List recetViewdProducts" +recetViewdProductsList);

            if (recetViewdProductsList.size() >= 6) {
                recetViewdProductsList.remove(5);   // Remove the last item if size exceeds
            }
            //If Product already Exists then Remove
        if(recetViewdProductsList.contains(productId)) {
            recetViewdProductsList.remove(productId);
        }
                //Add At 0th Location
            recetViewdProductsList.add(0,productId);

            recentViewHistory.setProductIds(recetViewdProductsList);
            // Maintain the list size. up to 6 items
            kafkaTemplate.send(AppConstants.RECENTLY_VIEWD_PRODUCT_Topic,userId,recentViewHistory);


        }
    }


}
