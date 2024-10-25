package com.osc.dashboardservice.mapper;

import com.Product.Product;
import com.Product.ProductDataResponse;
import com.osc.dashboardservice.dto.ApiResponse;
import com.osc.dashboardservice.dto.ProductResponseDto;
import com.osc.dashboardservice.dto.SimilarProductDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Component
public class MapToProductDetailDataObject {
    public ApiResponse map(ProductDataResponse productDataResponse) {
        Product responseProduct = productDataResponse.getProduct();
        ProductResponseDto similarProductDTO = new ProductResponseDto();
        similarProductDTO.setType("Similar Products");

        List<SimilarProductDto> similarProducts = new ArrayList<>();

        for (Product similarProduct : productDataResponse.getProductsList()) {
            SimilarProductDto minimalProductDTO = new SimilarProductDto();
            minimalProductDTO.setProductId(similarProduct.getProductId());
            minimalProductDTO.setProdName(similarProduct.getName());
            minimalProductDTO.setProdMarketPrice(String.valueOf(similarProduct.getPrice()));
            similarProducts.add(minimalProductDTO);
        }

        Map<String, Object> dataObject = Map.of(
                "prodId", productDataResponse.getProduct().getProductId(),
                "catId", responseProduct.getCategoryId(),
                "prodName", responseProduct.getName(),
                "prodDesc", responseProduct.getProductDetails(),
                "prodPrice", responseProduct.getPrice(),
                "similarProducts", similarProducts
        );

        return new ApiResponse(200,dataObject);
    }
}
