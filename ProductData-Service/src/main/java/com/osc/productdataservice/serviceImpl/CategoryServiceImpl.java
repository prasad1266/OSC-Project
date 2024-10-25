package com.osc.productdataservice.serviceImpl;

import com.osc.avro.files.Category;
import com.osc.avro.files.ProductViewCount;
import com.osc.productdataservice.service.CategoryService;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private  ReadOnlyKeyValueStore<String, ProductViewCount> productViewCountStore;
    private  ReadOnlyKeyValueStore<String, Category> categoryStore;

    public CategoryServiceImpl(@Qualifier("productViewCountStore") ReadOnlyKeyValueStore<String, ProductViewCount> productViewCountStore,
                               @Qualifier("categoryStore") ReadOnlyKeyValueStore<String, Category> categoryStore) {
        this.productViewCountStore = productViewCountStore;
        this.categoryStore = categoryStore;
    }

    @Override
    public List<com.Product.Category> getCategories() {

            // Fetch the category view count map from the store
            Map<CharSequence, Long> categoryViewCountMap = getMapofCategories();
           // System.out.println("categoryViewCountMap: " + categoryViewCountMap);

        return categoryViewCountMap.entrySet().stream()
                // Sort by viewCount in descending order, and if viewCount is equal, sort by categoryId alphabetically
                .sorted((entry1, entry2) -> {
                    int compareViewCount = Long.compare(entry2.getValue(), entry1.getValue()); // Sort by viewCount descending
                    if (compareViewCount != 0) {
                        return compareViewCount;
                    }
                    return entry1.getKey().toString().compareTo(entry2.getKey().toString()); // Sort by categoryId ascending if counts are equal
                }).limit(6)
                .map(entry -> {
                    String categoryId = entry.getKey().toString();
                    long viewCount = entry.getValue();

                    // Retrieve the Category from the categoryStore using categoryId
                    Category category = categoryStore.get(categoryId);
                    System.out.println("CAtegory is : "+category);
                    if (category != null) {
                        // Map the Category Avro object to Proto Category
                        return com.Product.Category.newBuilder()
                                .setCategoryId(categoryId)
                                .setCategoryName(category.getCategoryName().toString())
                                .setCount(viewCount)
                                .build();
                    } else {
                        // Log a message if the category is not found
                        System.out.println("Category not found for categoryId: " + categoryId);
                        return null;
                    }
                })
                // Filter out null values in case a category wasn't found
                .filter(categoryProto -> categoryProto != null)
                .collect(Collectors.toList());
        }

//        Map<CharSequence, Long> categoryViewCountMap = getMapofCategories();
//        System.out.println("categoryViewCountMap    "+categoryViewCountMap);
//
//        KeyValueIterator<String, Category> all = categoryStore.all();
//
//        return categoryViewCountMap.entrySet().stream().map(entry -> {
//            String categoryId = entry.getKey().toString();
////            System.out.println("categoryId : "+categoryId);
//            long viewCount = entry.getValue();
//            Category category = categoryStore.get(categoryId);
//           // System.out.println("CAtegory Avro   "+category);
//          //  System.out.println("CAtegory ****** "+category.getCategoryName().toString());
//            return com.Product.Category.newBuilder().setCategoryId(categoryId).setCategoryName(category.getCategoryName().toString()).setCount(viewCount).build();
//        }).collect(Collectors.toList());


    public Map<CharSequence, Long> getMapofCategories() {
        Map<String, ProductViewCount> productViewCountMap = new HashMap<>();
        KeyValueIterator<String, ProductViewCount> iterator = productViewCountStore.all();

        while (iterator.hasNext()) {
            KeyValue<String, ProductViewCount> entry = iterator.next();
            productViewCountMap.put(entry.key, entry.value);
        }

        return productViewCountMap.values().stream()
                .collect(Collectors.groupingBy(
                        ProductViewCount::getCategoryId,
                        Collectors.summingLong(ProductViewCount::getViewCount)));
    }
//    public Map<CharSequence, Long> getMapofCategories(){
//        Map<String, ProductViewCount> productViewCountMap =new HashMap<>();
//        KeyValueIterator<String, ProductViewCount> iterator = productViewCountStore.all();
//
//            while (iterator.hasNext()) {
//                KeyValue<String, ProductViewCount> entry = iterator.next();
//                productViewCountMap.put(entry.key, entry.value);
//            }
//
//        return productViewCountMap.values().stream()
//                .collect(Collectors.groupingBy(
//                        ProductViewCount::getCategoryId,
//                        Collectors.summingLong(ProductViewCount::getViewCount)));
//
//    }
}
