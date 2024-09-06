package com.osc.sessiondataservice.kafka;

import com.osc.sessiondataservice.avro.SessionAvro;
import com.osc.sessiondataservice.constants.Constants;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

import java.util.HashMap;
import java.util.Map;

public class AppSerdes extends Serdes {
    public static Serde<SessionAvro> SessionSerde() {
        SpecificAvroSerde<SessionAvro> serde = new SpecificAvroSerde<>();
        Map<String, String> serdeConfig = new HashMap<>();
        serdeConfig.put("schema.registry.url", Constants.SCHEMA_REGISTRY_URL);
        serde.configure(serdeConfig, false);
        return serde;
    }
}
