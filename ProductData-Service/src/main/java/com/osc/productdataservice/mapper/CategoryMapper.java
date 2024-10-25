package com.osc.productdataservice.mapper;

import com.osc.avro.files.Category;
import com.osc.productdataservice.entity.Categories;

public class CategoryMapper {
    public static Category mapToCategoryAvro(Categories entity) {
        return Category.newBuilder()
                .setCategoryName(entity.getCategoryName())
                .setImagePath(entity.getImagePath())
                .build();
    }
}
