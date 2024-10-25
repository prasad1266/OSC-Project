package com.osc.dashboardservice.response;

import com.Product.DashboardDetails;
import com.osc.dashboardservice.dto.*;
import com.osc.dashboardservice.mapper.MapCategoriesToDto;
import com.osc.dashboardservice.mapper.MapProductsToDTO;

import java.util.List;

public class ResponseMapping {
    public static DataObject map(DashboardDetails dashboardDetails) {
        DataObject dataObject;

        // Map featured products to DTO
        DashboardFeaturedProductsDTO dashboardFeaturedProductsDTO = new DashboardFeaturedProductsDTO(
                "Featured Products",
                MapProductsToDTO.mapProductToDTO(dashboardDetails.getFeaturedProductsList())
        );

        // Map categories to DTO
        DashboardCategoryListDTO dashboardCategoryListDTO = new DashboardCategoryListDTO(
                "Categories",
                MapCategoriesToDto.mapCategoryProtoToDTO(dashboardDetails.getCategoriesList())
        );

        // Check if recently viewed products list is not empty
        if (!dashboardDetails.getRecentlyViewedProductsList().isEmpty()) {
            // Map similar products to DTO
            DashboardSimilarProductDTO dashboardSimilarProductDTO = new DashboardSimilarProductDTO(
                    "Similar Products",
                    MapProductsToDTO.mapProductToDTO(dashboardDetails.getSimilarProductsList())
            );

            // Map recently viewed products to DTO
            DashboardRecentlyViewedProductDTO dashboardRecentlyViewedProductDTO = new DashboardRecentlyViewedProductDTO(
                    "Recently Viewed Products",
                    MapProductsToDTO.mapProductToDTO(dashboardDetails.getRecentlyViewedProductsList())
            );

            // Create data object with recently viewed and similar products
            dataObject = new DataObject(
                    List.of(dashboardRecentlyViewedProductDTO, dashboardSimilarProductDTO, dashboardCategoryListDTO)
            );

            return dataObject;
        }

        // Create data object with category and featured products
        dataObject = new DataObject(
                List.of(dashboardCategoryListDTO, dashboardFeaturedProductsDTO)
        );

        return dataObject;
    }

}
