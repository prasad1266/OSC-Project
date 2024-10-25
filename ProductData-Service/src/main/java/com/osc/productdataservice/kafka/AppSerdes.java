package com.osc.productdataservice.kafka;

import com.osc.avro.files.Category;
import com.osc.avro.files.Product;
import com.osc.avro.files.ProductViewCount;
import com.osc.productdataservice.constants.AppConstants;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

import java.util.HashMap;
import java.util.Map;
//recentViewdProductSerdes
public class AppSerdes extends Serdes {

    public static Serde<Product> productSerdes() {
        SpecificAvroSerde<Product> serde = new SpecificAvroSerde<>();
        Map<String, String> serdeConfig = new HashMap<>();
        serdeConfig.put("schema.registry.url", AppConstants.SCHEMA_REGISTRY_URL);
        serde.configure(serdeConfig, false);
        return serde;
    }

    public static Serde<Category> categorySerdes() {
        SpecificAvroSerde<Category> serde = new SpecificAvroSerde<>();
        Map<String, String> serdeConfig = new HashMap<>();
        serdeConfig.put("schema.registry.url", AppConstants.SCHEMA_REGISTRY_URL);
        serde.configure(serdeConfig, false);
        return serde;
    }

    public static Serde<ProductViewCount> productViewCountSerdes() {
        SpecificAvroSerde<ProductViewCount> serde = new SpecificAvroSerde<>();
        Map<String, String> serdeConfig = new HashMap<>();
        serdeConfig.put("schema.registry.url", AppConstants.SCHEMA_REGISTRY_URL);
        serde.configure(serdeConfig, false);
        return serde;
    }
}
