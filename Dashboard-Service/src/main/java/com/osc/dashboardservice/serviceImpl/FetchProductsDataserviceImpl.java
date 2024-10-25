package com.osc.dashboardservice.serviceImpl;

import com.Product.DashboardRequest;
import com.Product.DashboardResponse;
import com.Product.ProductDataServiceGrpc;
import com.osc.dashboardservice.response.ResponseMapping;
import com.osc.dashboardservice.dto.ApiResponse;
import com.osc.dashboardservice.dto.DataObject;
import com.osc.dashboardservice.service.FetchProductsDataservice;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FetchProductsDataserviceImpl implements FetchProductsDataservice {

    private ProductDataServiceGrpc.ProductDataServiceBlockingStub productDataServiceBlockingStub;

    public FetchProductsDataserviceImpl(@Qualifier("productDataServiceBlockingStub")ProductDataServiceGrpc.ProductDataServiceBlockingStub productDataServiceBlockingStub) {
        this.productDataServiceBlockingStub = productDataServiceBlockingStub;
    }

    @Override
    public ResponseEntity<ApiResponse> getUserProductData(DashboardRequest request) {

        DashboardResponse response = productDataServiceBlockingStub.fetchProductDashboard(request);
        DataObject dataObject = ResponseMapping.map(response.getDashboardDetails());
        return  new ResponseEntity<>(new ApiResponse(200,dataObject), HttpStatusCode.valueOf(200));

    }
}
