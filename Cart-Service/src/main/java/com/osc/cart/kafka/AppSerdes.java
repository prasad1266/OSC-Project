package com.osc.cart.kafka;


import com.osc.avro.files.CartList;
import com.osc.cart.Constants.AppConstants;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

import java.util.HashMap;
import java.util.Map;


public class AppSerdes extends Serdes {

    public static Serde<CartList> cartListSerde() {
        SpecificAvroSerde<CartList> serde = new SpecificAvroSerde<>();
        Map<String, String> serdeConfig = new HashMap<>();
        serdeConfig.put("schema.registry.url", AppConstants.SCHEMA_REGISTRY_URL);
        serde.configure(serdeConfig, false);
        return serde;
    }
}
