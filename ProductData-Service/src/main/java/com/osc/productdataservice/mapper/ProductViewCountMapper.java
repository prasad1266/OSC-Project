package com.osc.productdataservice.mapper;

import com.osc.avro.files.ProductViewCount;
import com.osc.productdataservice.entity.ProductCount;

public class ProductViewCountMapper {
    public static ProductViewCount mapToProductCountAvro(ProductCount entity) {
        return ProductViewCount.newBuilder()
                .setCategoryId(String.valueOf(entity.getCategoryId()))
                .setViewCount(entity.getViewCount())
                .build();
    }
}
