package com.osc.recentlyviewservice.mapper;

import com.osc.recentlyviewservice.entity.RecentViewHistoryEntity;
import com.recentHistory.RecentlyViewedResponse;

public class RecentViewdProductsMapper {
    public static com.osc.avro.files.RecentViewHistory toRecentViewdProductAvro(RecentViewHistoryEntity recentViewHistoryEntity) {
//        com.osc.avro.files.RecentViewHistoryEntity recentViewHistory1 =  com.osc.avro.files.RecentViewHistoryEntity.newBuilder().build();
//for(CharSequence products :recentViewHistoryEntity.getProductIds()) {
//    recentViewHistory1.setProductIds(products)
//            .build();
//}
        return null;
    }

    public static RecentlyViewedResponse mapToRecentlyViewedResponse(com.osc.avro.files.RecentViewHistory recentViewHistory) {

        RecentlyViewedResponse.Builder responseBuilder = RecentlyViewedResponse.newBuilder();
        for (CharSequence productId : recentViewHistory.getProductIds()) {
            responseBuilder.addProductId(productId.toString());
        }
        return responseBuilder.build();
    }
}
