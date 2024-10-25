package com.osc.dashboardservice.mapper;

import com.Product.Category;
import com.osc.dashboardservice.dto.DashboardCategoryDTO;

import java.util.List;
import java.util.stream.Collectors;

public class MapCategoriesToDto {
    public static List<DashboardCategoryDTO> mapCategoryProtoToDTO(List<Category> categoryList) {
        return categoryList.stream().map(category -> new DashboardCategoryDTO(
                category.getCategoryId(),
                category.getCategoryName(),
                category.getCount()
        )).collect(Collectors.toList());
    }
}
