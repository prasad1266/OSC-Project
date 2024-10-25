package com.osc.cart.configuration;


import com.osc.avro.files.CartList;
import com.osc.avro.files.RecentViewHistory;
import com.osc.cart.Constants.AppConstants;
import com.osc.cart.kafka.AppSerdes;
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

    @Bean(name = "Cart-producer")
    public ProducerFactory<String, CartList> producerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, AppConstants.BOOTSTRAP_SERVER);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        props.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, AppConstants.SCHEMA_REGISTRY_URL);
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean(name = "kafkaTemaplate")
    public KafkaTemplate<String, CartList> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean(name = "Cart-products-topic")
    public NewTopic createCartProductsTopic() {
        return new NewTopic(AppConstants.CART_PRODUCT_Topic, 12, (short) 1);
    }

    @Bean
    @Qualifier("Cart_product-streams-properties")
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
    public KafkaStreams kafkaStreams(@Qualifier("Cart_product-streams-properties") Properties streamsProperties,
                                     @Qualifier("streams-builder") StreamsBuilder streamsBuilder) {
        cartProductsKTable(streamsBuilder);

        KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(), streamsProperties);
        kafkaStreams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
        return kafkaStreams;
    }

    @Bean(name = "Cart-Products-Store")
    public ReadOnlyKeyValueStore<String, CartList> cartProductsStore(@Qualifier("kafka-streams") KafkaStreams kafkaStreams) throws InterruptedException {
        while (kafkaStreams.state() != KafkaStreams.State.RUNNING) {
            Thread.sleep(100);
        }
        return kafkaStreams.store(StoreQueryParameters.fromNameAndType(AppConstants.CART_PRODUCT_Topic_STORE, QueryableStoreTypes.keyValueStore()));
    }


    @Bean(name = "Cart-products-KTable")
    public KTable<String,CartList > cartProductsKTable(@Qualifier("streams-builder") StreamsBuilder streamsBuilder) {
        return streamsBuilder.table(
                AppConstants.CART_PRODUCT_Topic,
                Consumed.with(Serdes.String(), AppSerdes.cartListSerde()),
                Materialized.<String, CartList, KeyValueStore<Bytes, byte[]>>as(AppConstants.CART_PRODUCT_Topic_STORE)
                        .withKeySerde(Serdes.String())
                        .withValueSerde(AppSerdes.cartListSerde())
        );
    }

}

