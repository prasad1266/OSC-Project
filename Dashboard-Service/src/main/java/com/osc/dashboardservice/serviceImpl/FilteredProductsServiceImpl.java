package com.osc.dashboardservice.serviceImpl;

import com.Product.FilterProductRequest;
import com.Product.FilterProductResponse;
import com.Product.ProductDataServiceGrpc;
import com.osc.dashboardservice.dto.*;
import com.osc.dashboardservice.mapper.MapProductsToDTO;
import com.osc.dashboardservice.mapper.RequestMapper;
import com.osc.dashboardservice.service.FilteredProductsService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FilteredProductsServiceImpl implements FilteredProductsService {

    private ProductDataServiceGrpc.ProductDataServiceBlockingStub productDataServiceBlockingStub;

    public FilteredProductsServiceImpl(ProductDataServiceGrpc.ProductDataServiceBlockingStub productDataServiceBlockingStub) {
        this.productDataServiceBlockingStub = productDataServiceBlockingStub;
    }

    @Override
    public ResponseEntity<ApiResponse> getFilterdProducts(FilterProductRequestDto filterProductRequestDto) {
        System.out.println(filterProductRequestDto);
        FilterProductRequest request =   RequestMapper.mapToFilterProductRequest(filterProductRequestDto);

        FilterProductResponse response =   productDataServiceBlockingStub.fetchFilteredProducts(request);

        System.out.println("FilterProductResponse "+response);

        Map<String, List<DashboardProductDTO>> products = Map.of("products", MapProductsToDTO.mapProductToDTO(response.getProductsList()));

       return ResponseEntity.ok(new ApiResponse(200,products));
    }
}
