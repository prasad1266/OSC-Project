package com.osc.recentlyviewservice.kafka;



import com.osc.avro.files.RecentViewHistory;
import com.osc.recentlyviewservice.Constants.AppConstants;
import com.osc.recentlyviewservice.mapper.RecentViewdProductsMapper;
import com.osc.recentlyviewservice.repository.RecentViewHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class DataProducer {


    @Autowired
    private RecentViewHistoryRepository recentViewHistoryRepository;


    private KafkaTemplate<String, RecentViewHistory> productKafkaTemplate;

//
//    private KafkaTemplate<String, Category> categoryKafkaTemplate;
//
//
//    private KafkaTemplate<String, ProductViewCount> productViewCountKafkaTemplate;


    public DataProducer(@Qualifier("kafkaTemaplate")KafkaTemplate<String, RecentViewHistory> productKafkaTemplate) {
        this.productKafkaTemplate = productKafkaTemplate;
    }

    // Runner that executes at startup
//    @Bean
//    public ApplicationRunner runner() {
//        return args -> {
//
//           List<com.osc.recentlyviewservice.entity.RecentViewHistoryEntity> recentViewHistories = recentViewHistoryRepository.findAll();
//
//            // Produce product data to product-topic
//
//
//            for (com.osc.recentlyviewservice.entity.RecentViewHistoryEntity recentViewHistory : recentViewHistories) {
////                if(!kafkaProducts.containsKey(product.getProductId()) )
//                RecentViewHistoryEntity productAvro = RecentViewdProductsMapper.toRecentViewdProductAvro(recentViewHistory);
//                productKafkaTemplate.send(AppConstants.RECENTLY_VIEWD_PRODUCT_Topic,recentViewHistory.getUserId(),productAvro);
//            }
//
//        };
//    }
}
