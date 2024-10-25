package com.osc.productdataservice.Utility;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MapCategoryToProductIds {
    public Map<String, List<String>> map(List<String> recentlyViewedProductIds) {

        Map<String, List<String>> categoryToProductIdsMap = new LinkedHashMap<>();

        for (String productId : recentlyViewedProductIds) {
            String categoryId = productId.substring(0, 1);

            categoryToProductIdsMap.computeIfAbsent(categoryId, k -> new ArrayList<>()).add(productId);
        }

        return categoryToProductIdsMap;
    }

}
