package com.osc.recentlyviewservice.configuration;


import com.osc.avro.files.RecentViewHistory;
import com.osc.recentlyviewservice.Constants.AppConstants;
import com.osc.recentlyviewservice.kafka.AppSerdes;
import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
class KafkaConfig {

    @Bean(name = "Recently-Viewd-producer")
    public ProducerFactory<String, RecentViewHistory> producerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, AppConstants.BOOTSTRAP_SERVER);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        props.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, AppConstants.SCHEMA_REGISTRY_URL);
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean(name = "kafkaTemaplate")
    public KafkaTemplate<String, RecentViewHistory> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

//    @Bean(name = "Recent-Viewd-products-topic")
//    public NewTopic createRecentViewdProductsTopic() {
//        return new NewTopic(AppConstants.RECENTLY_VIEWD_PRODUCT_Topic, 12, (short) 1);
//    }

    @Bean
    @Qualifier("RECENTLY_VIEWD_product-streams-properties")
    public Properties streamsProperties() {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, AppConstants.APPLICATION_ID);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, AppConstants.BOOTSTRAP_SERVER);
        props.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, AppConstants.SCHEMA_REGISTRY_URL);        // Uncomment for exactly-once processing
        props.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, StreamsConfig.EXACTLY_ONCE_V2);
        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 15);
        props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 100L);
        return props;
    }

    @Bean(name = "streams-builder")
    public StreamsBuilder streamsBuilder() {
        return new StreamsBuilder();
    }

    @Bean(name = "kafka-streams")
    public KafkaStreams kafkaStreams(@Qualifier("RECENTLY_VIEWD_product-streams-properties") Properties streamsProperties,
                                     @Qualifier("streams-builder") StreamsBuilder streamsBuilder) {
        recentViewdProductsKTable(streamsBuilder);

        KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(), streamsProperties);
        kafkaStreams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
        return kafkaStreams;
    }

    @Bean(name = "RecentViewHistoryStore")
    public ReadOnlyKeyValueStore<String, RecentViewHistory> recentViewdProductsStore(@Qualifier("kafka-streams") KafkaStreams kafkaStreams) throws InterruptedException {
        while (kafkaStreams.state() != KafkaStreams.State.RUNNING) {
            Thread.sleep(100);
        }
        return kafkaStreams.store(StoreQueryParameters.fromNameAndType(AppConstants.RECENTLY_VIEWD_PRODUCT_STORE, QueryableStoreTypes.keyValueStore()));
    }


    @Bean(name = "Recent-Viewd-products-KTable")
    public KTable<String,RecentViewHistory > recentViewdProductsKTable(@Qualifier("streams-builder") StreamsBuilder streamsBuilder) {
        return streamsBuilder.table(
                AppConstants.RECENTLY_VIEWD_PRODUCT_Topic,
                Consumed.with(Serdes.String(), AppSerdes.recentViewdProductSerdes()),
                Materialized.<String, RecentViewHistory, KeyValueStore<Bytes, byte[]>>as(AppConstants.RECENTLY_VIEWD_PRODUCT_STORE)
                        .withKeySerde(Serdes.String())
                        .withValueSerde(AppSerdes.recentViewdProductSerdes())
        );
    }

}

