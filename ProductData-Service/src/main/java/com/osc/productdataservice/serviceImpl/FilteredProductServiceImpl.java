package com.osc.productdataservice.serviceImpl;

import com.Product.FilterProductRequest;
import com.Product.FilterProductResponse;

import com.osc.avro.files.Product;
import com.osc.productdataservice.kafka.KafkaManagerService;
import com.osc.productdataservice.mapper.ProductMapper;
import com.osc.productdataservice.service.FilteredProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
@Service
public class FilteredProductServiceImpl implements FilteredProductService {
   private KafkaManagerService kafkaManagerService;
   private ProductMapper productMapper;

    public FilteredProductServiceImpl(KafkaManagerService kafkaManagerService, ProductMapper productMapper) {
        this.kafkaManagerService = kafkaManagerService;
        this.productMapper = productMapper;
    }

    @Override
    public FilterProductResponse getFilteredProducts(FilterProductRequest request) {
        Map<String, Product> productDetailsMap = productMapper.getProductsByCategory(request.getCategoryId());
        Map<String, Integer> viewCounts = kafkaManagerService.fetchProductViewCountData();

        List<Map.Entry<String, Product>> productEntries = new ArrayList<>(productDetailsMap.entrySet());


        switch (request.getFilter()) {
            case "LH":
                productEntries.sort(Comparator.comparingDouble(entry -> entry.getValue().getProductPrice()));
                break;

            case "HL":
                productEntries.sort(Comparator.comparingDouble((Map.Entry<String, Product> entry) ->
                        entry.getValue().getProductPrice()).reversed());
                break;

            case "P":
                productEntries.sort((entry1, entry2) -> {
                    int count1 = viewCounts.getOrDefault(entry1.getKey(), 0);
                    int count2 = viewCounts.getOrDefault(entry2.getKey(), 0);
                    return Integer.compare(count2, count1);
                });
                break;

            case "NF":
                productEntries.sort(Comparator.comparingLong((Map.Entry<String, Product> entry) ->
                        entry.getValue().getAddedDateTime()).reversed());
                break;

            default:
                break;
        }

        return productMapper.map(productEntries);

    }
}




