package com.osc.dashboardservice.mapper;

import com.Product.Product;
import com.osc.dashboardservice.dto.DashboardProductDTO;

import java.util.List;
import java.util.stream.Collectors;

public class MapProductsToDTO {
    public static List<DashboardProductDTO> mapProductToDTO(List<Product> productList) {
        return productList.stream().map(product -> new DashboardProductDTO(
                product.getProductId(),        // productId
                product.getName(),             // prodName
                String.valueOf(product.getPrice()), // prodMarketPrice
                product.getCategoryId(),       // categoryId
                product.getProductDetails()    // productDescription
        )).collect(Collectors.toList());
    }
}
