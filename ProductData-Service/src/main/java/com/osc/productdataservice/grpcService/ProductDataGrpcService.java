package com.osc.productdataservice.grpcService;

import com.Product.*;
import com.osc.productdataservice.mapper.ProductMapper;
import com.osc.productdataservice.service.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;

@GrpcService
public class ProductDataGrpcService extends ProductDataServiceGrpc.ProductDataServiceImplBase {

    private ProductMapper productMapper;
    private CategoryService categoryService;
    private SimilarProductsService similarProductsService;
    private FeatureProductService featureProductService;
    private GetProductDetails getProductDetails;
    private FilteredProductService filteredProductService;
    private  GetCartProductDetailsService getCartProductDetailsService;



    public ProductDataGrpcService(SimilarProductsService similarProductsService, CategoryService categoryService,
                                  FeatureProductService featureProductService,
                                  ProductMapper productMapper,
                                  GetProductDetails getProductDetails, FilteredProductService filteredProductService, GetCartProductDetailsService getCartProductDetailsService) {
        this.similarProductsService = similarProductsService;
        this.categoryService = categoryService;
        this.featureProductService = featureProductService;
        this.productMapper = productMapper;
        this.getProductDetails = getProductDetails;
        this.filteredProductService = filteredProductService;
        this.getCartProductDetailsService = getCartProductDetailsService;
    }


    @Override
    public void fetchProductDashboard(DashboardRequest request, StreamObserver<DashboardResponse> responseObserver) {
      // if have RecentView History
        if(!request.getProductIdList().isEmpty()){

            List<String> recentlyViewdProductsIds =  ProductMapper.mapToRecentlyViewList(request);

           // getCategories
             List<com.Product.Category>  categoryList =  categoryService.getCategories();

            //Similar Products
            List<String> similarProductsIds = similarProductsService.fetchSimilarProductsIds(recentlyViewdProductsIds);
            List<Product> similarProductsList =  productMapper.fetchAndMapToProto(similarProductsIds);

            List<Product> recentlyViewdProductsList = productMapper.fetchAndMapToProto(recentlyViewdProductsIds);
            DashboardDetails dashboardDetails =  DashboardDetails.newBuilder().addAllCategories(categoryList).addAllSimilarProducts(similarProductsList)
                    .addAllRecentlyViewedProducts(recentlyViewdProductsList).build();

            DashboardResponse dashboardResponse =  DashboardResponse.newBuilder().setDashboardDetails(dashboardDetails).build();

            responseObserver.onNext(dashboardResponse);
            responseObserver.onCompleted();

        }           //if don't have RecentView History
        else {
            List<com.Product.Category>  categoryList =  categoryService.getCategories();
            List<Product> featureProductsList = featureProductService.getFeatureProducts();
           DashboardDetails dashboardDetails =  DashboardDetails.newBuilder().addAllCategories(categoryList).addAllFeaturedProducts(featureProductsList).build();
           DashboardResponse dashboardResponse =  DashboardResponse.newBuilder().setDashboardDetails(dashboardDetails).build();

            responseObserver.onNext(dashboardResponse);
            responseObserver.onCompleted();
        }

    }

    @Override
    public void fetchProductDetails(ProductDataRequest request, StreamObserver<ProductDataResponse> responseObserver) {
        ProductDataResponse response =  getProductDetails.getDetails(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void fetchFilteredProducts(FilterProductRequest request, StreamObserver<FilterProductResponse> responseObserver) {
        FilterProductResponse response =  filteredProductService.getFilteredProducts(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
    @Override
    public void getCartProductDetails(GetCartProductDetailsRequest request, StreamObserver<GetCartProductDetailsResponse> responseObserver) {
        GetCartProductDetailsResponse getCartProductDetailsResponse  = getCartProductDetailsService.getCartProducts(request);
        responseObserver.onNext(getCartProductDetailsResponse);
        responseObserver.onCompleted();
    }
}
