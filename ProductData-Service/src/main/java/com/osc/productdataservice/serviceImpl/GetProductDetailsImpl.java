package com.osc.productdataservice.serviceImpl;

import com.Product.ProductDataRequest;
import com.Product.ProductDataResponse;
import com.osc.avro.files.Category;
import com.osc.avro.files.Product;
import com.osc.productdataservice.kafka.KafkaManagerService;
import com.osc.productdataservice.mapper.ProductMapper;
import com.osc.productdataservice.service.GetProductDetails;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GetProductDetailsImpl implements GetProductDetails {
    @Autowired
    private KafkaManagerService kafkaManagerService;
    private ProductMapper productMapper;
    private ReadOnlyKeyValueStore<String, Product> productStore;

    public GetProductDetailsImpl(@Qualifier("productStore") ReadOnlyKeyValueStore<String, Product> productStore,
                                 ProductMapper productMapper, @Qualifier("categoryStore") ReadOnlyKeyValueStore<String, Category> categoryReadOnlyKeyValueStore) {
        this.productStore = productStore;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDataResponse getDetails(ProductDataRequest request) {
        String productId =request.getProductId();
       Product product = productStore.get(productId);

//String CategoryId = productId.substring(0,1);
        com.Product.Product product1 = productMapper.mapAvroToProto(product,productId);
        System.out.println("product1 : "+product1);
        //Increase viewCOunt By One
        kafkaManagerService.updateViewCount(productId);

      List<com.Product.Product> listOfSimilarProduct = getListOfSimilarProducts(request.getProductId());

        ProductDataResponse response =  ProductDataResponse.newBuilder().setProduct(product1).addAllProducts(listOfSimilarProduct).build();
        System.out.println("*************** "+response);
        return response;
    }


    private List<com.Product.Product> getListOfSimilarProducts(String productId) {

        Map<String, Integer> productViewCountMap = kafkaManagerService.fetchProductViewCountData();
        String CategoryId = productId.substring(0,1);

        List<String> similarProductIds = productViewCountMap.keySet().stream()
                .filter(id -> !id.equals(productId) &&  id.startsWith(CategoryId)) // Exclude the current product
                .sorted((id1, id2) -> productViewCountMap.get(id2) - productViewCountMap.get(id1)) // Sort by view count descending
                .limit(6) // Limit to 6 products
                .collect(Collectors.toList());

        return productMapper.fetchAndMapToProto(similarProductIds);
    }


}
