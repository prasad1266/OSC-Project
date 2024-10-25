//package com.osc.productdataservice.serviceImpl;
//
//import com.osc.avro.files.Product;
//import com.osc.avro.files.ProductViewCount;
//import com.osc.productdataservice.sevice.SimilarProductsService;
//import org.apache.kafka.streams.KeyValue;
//import org.apache.kafka.streams.state.KeyValueIterator;
//import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//import java.util.stream.Collectors;
//import java.util.stream.StreamSupport;
//
//@Service
//public class SimilarProductsServiceImpl implements SimilarProductsService {
//
//    private ReadOnlyKeyValueStore<String, ProductViewCount> productViewCountStore;
//
//
//    public SimilarProductsServiceImpl(ReadOnlyKeyValueStore<String, ProductViewCount> productViewCountStore) {
//        this.productViewCountStore = productViewCountStore;
//    }
//
//    @Override
//    public List<Product> fetchSimilarProductsIds(List<String> recentViewdProdutIds) {
//        List<Product> similarProducts = new ArrayList<>();
//
//        // Iterate over each last viewed product
//        for (String productId : recentViewdProdutIds) {
//
//            // Fetch the category of the product
//            String categoryId = productId.substring(0, 1);
//
//            // Get top-ranked product for the category, ensuring it’s not in the last viewed or similar products list
//            Product topProduct = getTopProductForCategory(categoryId, recentViewdProdutIds, similarProducts);
//
//            if (topProduct != null) {
//                similarProducts.add(topProduct);
//            }
//        }
//
//        // If fewer than 6 similar products, fill remaining slots from most recent category
//        fillRemainingSlots(similarProducts, recentViewdProdutIds);
//
//        return similarProducts;
//
////return  null;
//    }
//    public Product getTopProductForCategory(String categoryId, List<String> lastViewedProductIds, List<Product> similarProducts) {
////        List<Product> topRankedProducts = kafkaConsumer.readTopProductsByCategory("Product-view-count", categoryId);
//       // KeyValueIterator<String, ProductViewCount> ReadOnlyKeyValueStore = productViewCountStore.all();
//        Map<String, ProductViewCount> productViewCountMap = new HashMap<>();
//
//        try (KeyValueIterator<String, ProductViewCount> iterator = productViewCountStore.all()) {
//            while (iterator.hasNext()) {
//                KeyValue<String, ProductViewCount> entry = iterator.next();
//                productViewCountMap.putIfAbsent(entry.key, entry.value);
//            }
//        }
//
//
//
//        // Iterate through the top-ranked products for the category
//        for (Product rankedProduct : topRankedProducts) {
//            if (!lastViewedProductIds.contains(rankedProduct.getProductId())
//                    && !similarProducts.contains(rankedProduct)) {
//                return rankedProduct;  // Return the first valid product not in the last viewed or similar lists
//            }
//        }
//
//        return null; // If no valid product found, return null
//    }
//
//    public void fillRemainingSlots(List<Product> similarProducts, List<String> lastViewedProductIds) {
//        if (similarProducts.size() < 6) {
//            // Get the most recent category
//            String lastViewedCategory = kafkaConsumer.readFromTopic("product-topic", lastViewedProductIds.get(lastViewedProductIds.size() - 1)).getCategoryId();
//
//            List<Product> additionalProducts = kafkaConsumer.readTopProductsByCategory("Product-view-count", lastViewedCategory);
//
//            for (Product additionalProduct : additionalProducts) {
//                if (!lastViewedProductIds.contains(additionalProduct.getProductId())
//                        && !similarProducts.contains(additionalProduct)) {
//                    similarProducts.add(additionalProduct); // Add the next top-ranked product
//                }
//
//                // Stop once we reach 6 similar products
//                if (similarProducts.size() == 6) break;
//            }
//        }
//    }
//
//}
