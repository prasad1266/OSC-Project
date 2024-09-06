package com.osc.userservice.configuration;

import avro.OtpMessage;
import avro.User;
import com.osc.userservice.constatnts.Constants;
import com.osc.userservice.kafka.AppSerdes;
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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class KafkaConfig {

    @Bean(name = "user-producer")
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Constants.bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        configProps.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, Constants.SCHEMA_REGISTRY_URL);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean(name = "KafkaTemplate")
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean(name = "users-topic")
    public NewTopic createUserTopic() {
        return new NewTopic(Constants.User_Topic, 12, (short) 1);
    }

    @Bean(name = "otp-topic")
    public NewTopic createOTPTopic() {
        return new NewTopic(Constants.OTP_Topic, 12, (short) 1);
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

    @Bean(name = "streams")
    public KafkaStreams kafkaStreams(
            @Qualifier("streams-properties") Properties streamsProperties,
            @Qualifier("streams-builder") StreamsBuilder streamsBuilder) {
        otpKTable(streamsBuilder);
        userKTable(streamsBuilder);

        KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(), streamsProperties);
        kafkaStreams.start();

        // Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
        return kafkaStreams;
    }

    @Bean
    public ReadOnlyKeyValueStore<String, OtpMessage> otpStore(
            @Qualifier("streams") KafkaStreams kafkaStreams) throws InterruptedException {

        // Wait for the KafkaStreams to be ready
        while (kafkaStreams.state() != KafkaStreams.State.RUNNING) {
            Thread.sleep(100);
        }

        return kafkaStreams.store(
                StoreQueryParameters.fromNameAndType(
                        Constants.OTP_STORE, QueryableStoreTypes.keyValueStore()
                )
        );
    }


    @Bean
    public ReadOnlyKeyValueStore<String, User> userStore(
            @Qualifier("streams") KafkaStreams kafkaStreams) throws InterruptedException {

        // Wait for the KafkaStreams to be ready
        while (kafkaStreams.state() != KafkaStreams.State.RUNNING) {
            Thread.sleep(100);
        }

        return kafkaStreams.store(
                StoreQueryParameters.fromNameAndType(
                        Constants.USER_STORE, QueryableStoreTypes.keyValueStore()
                )
        );
    }

    @Bean(name = "otp-KTable")
    public KTable<String, OtpMessage> otpKTable(@Qualifier("streams-builder") StreamsBuilder streamsBuilder) {
        return streamsBuilder.table(
                Constants.OTP_Topic,
                Consumed.with(Serdes.String(), AppSerdes.otpMessageSerde()),
                Materialized.<String, OtpMessage, KeyValueStore<org.apache.kafka.common.utils.Bytes, byte[]>>as(Constants.OTP_STORE)
                        .withKeySerde(Serdes.String())
                        .withValueSerde(AppSerdes.otpMessageSerde())
        );
    }

    @Bean(name = "user-KTable")
    public KTable<String, User> userKTable(@Qualifier("streams-builder") StreamsBuilder streamsBuilder) {
        return streamsBuilder.table(
                Constants.User_Topic,
                Consumed.with(Serdes.String(), AppSerdes.userSerde()),
                Materialized.<String, User, KeyValueStore<Bytes, byte[]>>as(Constants.USER_STORE)
                        .withKeySerde(Serdes.String())
                        .withValueSerde(AppSerdes.userSerde())
        );
    }

}
