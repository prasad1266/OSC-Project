package com.osc.productdataservice.kafka;

import com.osc.avro.files.Category;
import com.osc.avro.files.ProductViewCount;
import com.osc.productdataservice.repository.CategoryRepository;
import com.osc.productdataservice.repository.ProductCountRepository;
import com.osc.productdataservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class DataProducer {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductCountRepository productCountRepository;


    private KafkaTemplate<String, Object> productKafkaTemplate;


    private KafkaTemplate<String, Category> categoryKafkaTemplate;


    private KafkaTemplate<String, ProductViewCount> productViewCountKafkaTemplate;


    public DataProducer(@Qualifier("kafkaTemaplate")KafkaTemplate<String, Object> productKafkaTemplate) {
        this.productKafkaTemplate = productKafkaTemplate;
    }

////     Runner that executes at startup
  //  @Bean
  //  public ApplicationRunner runner() {
//        return args -> {
//
//            List<Products> products = productRepository.findAll();
//
//            List<Categories> categories = categoryRepository.findAll();
//            List<ProductCount> productCounts =productCountRepository.findAll();
//
//            // Produce product data to product-topic
////
//
//            for (Products product : products) {
////                if(!kafkaProducts.containsKey(product.getProductId()) )
//                Product productAvro = ProductMapper.toProductAvro(product);
//                productKafkaTemplate.send(AppConstants.Product_Details_Topic,product.getProductId().trim(),productAvro);
//            }
//
//            // Produce category data to category-topic
//            for (Categories category : categories) {
//                Category categoryAvro = CategoryMapper.mapToCategoryAvro(category);
//
//                productKafkaTemplate.send(AppConstants.Categories_Details_Topic,category.getCategoryId().toString(),categoryAvro);
//            }
//
//            // Produce product view count to product-view-count-topic
//            for (ProductCount productcount : productCounts) {
//                ProductViewCount productViewCountAvro = ProductViewCountMapper.mapToProductCountAvro(productcount);
//                productKafkaTemplate.send(AppConstants.Product_Count_Topic, productcount.getProductId(), productViewCountAvro);
//            }
//
//
//            // Print each category in the list
////            for (Categories category : categories) {
////                System.out.println(category);
////            }
//      };
   // }
}
