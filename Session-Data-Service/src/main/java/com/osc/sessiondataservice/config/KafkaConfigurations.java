package com.osc.sessiondataservice.config;

import com.osc.sessiondataservice.avro.SessionAvro;
import com.osc.sessiondataservice.constants.Constants;
import com.osc.sessiondataservice.kafka.AppSerdes;
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
import org.apache.kafka.streams.kstream.GlobalKTable;
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
public class KafkaConfigurations {
    @Bean(name = "session-producer")
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Constants.bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        props.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, Constants.SCHEMA_REGISTRY_URL);
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean(name = "KafkaTemplate")
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean(name = "session-topic")
    public NewTopic createSessionTopic() {
        return new NewTopic(Constants.SESSION_Topic, 12, (short) 1);
    }


    @Bean
    @Qualifier("streams-properties")
    public Properties streamsProperties() {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, Constants.applicationID);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, Constants.bootstrapServers);
        props.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, Constants.SCHEMA_REGISTRY_URL);
        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 15);
        props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_DOC, 100L);
        return props;
    }

    @Bean(name = "streams-builder")
    public StreamsBuilder streamsBuilder() {
        return new StreamsBuilder();
    }

    @Bean(name = "session-streams")
    public KafkaStreams kafkaStreams(
            @Qualifier("streams-properties") Properties streamsProperties,
            @Qualifier("streams-builder") StreamsBuilder streamsBuilder) {
        sessionKtable(streamsBuilder);

        KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(), streamsProperties);
        kafkaStreams.start();

         Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
        return kafkaStreams;
    }

    @Bean(name = "session-KTable")
    public GlobalKTable<String, SessionAvro> sessionKtable(@Qualifier("streams-builder") StreamsBuilder streamsBuilder) {
        return streamsBuilder.globalTable(
                Constants.SESSION_Topic,
                Consumed.with(Serdes.String(), AppSerdes.SessionSerde()),
                Materialized.<String, SessionAvro, KeyValueStore<Bytes, byte[]>>as(Constants.SESSION_STORE)
                        .withKeySerde(Serdes.String())
                        .withValueSerde(AppSerdes.SessionSerde())
        );
    }

    @Bean(name = "sessionStore")
    public ReadOnlyKeyValueStore<String, SessionAvro> sessionStore(
            @Qualifier("session-streams") KafkaStreams kafkaStreams) throws InterruptedException {

        // Wait for the KafkaStreams to be ready
        while (kafkaStreams.state() != KafkaStreams.State.RUNNING) {
            Thread.sleep(100);
        }

        return kafkaStreams.store(
                StoreQueryParameters.fromNameAndType(
                        Constants.SESSION_STORE, QueryableStoreTypes.keyValueStore()
                )
        );
    }

}
