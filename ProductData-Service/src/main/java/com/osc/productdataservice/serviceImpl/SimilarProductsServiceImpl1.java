package com.osc.productdataservice.serviceImpl;
import com.osc.avro.files.Product;
import com.osc.productdataservice.Utility.MapCategoryToProductIds;
import com.osc.productdataservice.Utility.TopProductFinder;
import com.osc.productdataservice.kafka.KafkaManagerService;
import com.osc.productdataservice.service.SimilarProductsService;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SimilarProductsServiceImpl1 implements SimilarProductsService {
    @Autowired
    private KafkaManagerService kafkaManagerService;
    @Autowired
    private MapCategoryToProductIds mapCategoryToProductIds;

    @Autowired
    private TopProductFinder topProductFinder;

   private ReadOnlyKeyValueStore<String, Product> productStore;

    public SimilarProductsServiceImpl1(ReadOnlyKeyValueStore<String, Product> productStore  ) {
        this.productStore = productStore;
    }


    @Override
    public List<String> fetchSimilarProductsIds(List<String> recentViewdProdutIdsList) {
        List<String> similarProductIdsList = new ArrayList<>();

        // Step 1: Extract category IDs from recently viewed product IDs
        Map<String, List<String>> categoryToProductIdsMap = mapCategoryToProductIds.map(recentViewdProdutIdsList);

        // Step 2: Get product view counts into a map
        Map<String, Integer> productViewCounts = kafkaManagerService.fetchProductViewCountData();

        // Step 3: Handle case for 6 recently viewed products

            for (String categoryId : categoryToProductIdsMap.keySet()) {
                // Fetch the top-ranked product for this category using the view counts
                String topProductId = topProductFinder.fetchTopProductId(
                        categoryId, recentViewdProdutIdsList, productViewCounts, similarProductIdsList
                );

                if (topProductId != null) {
                    similarProductIdsList.add(topProductId);
                }
            }

            // Step 4: If needed, fill remaining slots
            fillRemainingSlots(recentViewdProdutIdsList, similarProductIdsList, productViewCounts);
            return similarProductIdsList;

    }

    private void fillRemainingSlots(
            List<String> recentlyProductIdsList,
            List<String> similarProductIdsList,
            Map<String, Integer> productViewCounts
    ) {
        // Step 1: Get the most recently viewed category
        String mostRecentlyViewedCategoryId = recentlyProductIdsList.get(0).substring(0, 1);

        // Step 2: Fill remaining slots if there are less than 6 products
        while (similarProductIdsList.size() < 6) {
            String additionalProductId = topProductFinder.fetchTopProductId(
                    mostRecentlyViewedCategoryId, recentlyProductIdsList, productViewCounts, similarProductIdsList
            );

            if (additionalProductId != null) {
                similarProductIdsList.add(additionalProductId);
            } else {
                break;
            }
        }

    }



}
