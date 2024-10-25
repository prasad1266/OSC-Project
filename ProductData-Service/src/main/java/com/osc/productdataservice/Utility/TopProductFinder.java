package com.osc.productdataservice.Utility;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class TopProductFinder {
    public String fetchTopProductId(String identifier, List<String> recentlyProductIdsList,
                                    Map<String, Integer> productViewCounts,
                                    List<String> similarProductIdsList) {
        return productViewCounts.entrySet().stream().filter(entry -> entry.getKey().startsWith(identifier) && !recentlyProductIdsList.contains(entry.getKey()) && !similarProductIdsList.contains(entry.getKey())).max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(null);
    }

}
