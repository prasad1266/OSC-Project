package com.osc.dashboardservice.serviceImpl;

import com.Product.ProductDataRequest;
import com.Product.ProductDataResponse;
import com.Product.ProductDataServiceGrpc;
import com.osc.dashboardservice.dto.ApiResponse;
import com.osc.dashboardservice.dto.GetProductDetailsDto;
import com.osc.dashboardservice.mapper.MapToProductDetailDataObject;
import com.osc.dashboardservice.mapper.RequestMapper;
import com.osc.dashboardservice.service.ProductDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductDetailsServiceImpl implements ProductDetailsService {

    private MapToProductDetailDataObject mapToProductDetailDataObject;

    private ProductDataServiceGrpc.ProductDataServiceBlockingStub productDataServiceBlockingStub;

    public ProductDetailsServiceImpl(ProductDataServiceGrpc.ProductDataServiceBlockingStub productDataServiceBlockingStub,
                                        MapToProductDetailDataObject mapToProductDetailDataObject) {
        this.productDataServiceBlockingStub = productDataServiceBlockingStub;
        this.mapToProductDetailDataObject = mapToProductDetailDataObject;
    }

    @Override
    public ResponseEntity<ApiResponse> getProductDetails(GetProductDetailsDto getProductDetailsDto) {

        ProductDataRequest request = RequestMapper.mapToProductDataRequest(getProductDetailsDto);
        ProductDataResponse response = productDataServiceBlockingStub.fetchProductDetails(request);

        ApiResponse map = mapToProductDetailDataObject.map(response);

        return ResponseEntity.ok(map);
    }
}
