package com.flink;

import com.flink.models.LOAN;
import com.flink.models.LOAN_1;
import com.flink.models.LOAN_2;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.connector.base.DeliveryGuarantee;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.formats.json.JsonDeserializationSchema;
import org.apache.flink.formats.json.JsonSerializationSchema;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.core.JsonGenerator;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;

import java.io.InputStream;
import java.util.Properties;

public class LoanTransformationJob {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        Properties consumerConfig = new Properties();

        try (InputStream stream = LoanTransformationJob.class.getClassLoader().getResourceAsStream("consumer.properties")) {
            consumerConfig.load(stream);
        }

        Properties producerConfig = new Properties();
        try (InputStream stream = LoanTransformationJob.class.getClassLoader().getResourceAsStream("producer.properties")) {
            producerConfig.load(stream);
        }

        KafkaSource<LOAN_1> kafkaLoanSource1 =  KafkaSource.<LOAN_1>builder()
                .setProperties(consumerConfig)
                .setTopics("LOAN_1")
                .setStartingOffsets(OffsetsInitializer.latest())
                .setValueOnlyDeserializer(new JsonDeserializationSchema<>(LOAN_1.class))
                .build();

        KafkaSource<LOAN_2> kafkaLoanSource2 =  KafkaSource.<LOAN_2>builder()
                .setProperties(consumerConfig)
                .setTopics("LOAN_2")
                .setStartingOffsets(OffsetsInitializer.latest())
                .setValueOnlyDeserializer(new JsonDeserializationSchema<>(LOAN_2.class))
                .build();

        DataStream<LOAN_1 > loanStream1 = env.fromSource(kafkaLoanSource1, WatermarkStrategy.noWatermarks(), "loan_source_1");
        DataStream<LOAN_2 > loanStream2 = env.fromSource(kafkaLoanSource2, WatermarkStrategy.noWatermarks(), "loan_source_2");

        KafkaRecordSerializationSchema<LOAN> loanSerializer = KafkaRecordSerializationSchema.<LOAN>builder()
                .setTopic("LOAN")
                .setKeySerializationSchema(loan -> String.valueOf(loan.getInvstr_loan_nbr()).getBytes())
                .setValueSerializationSchema(new JsonSerializationSchema<LOAN>(
                        () -> {
                            return new ObjectMapper()
                                    .configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, false)
                                    .registerModule(new JavaTimeModule());
                        }
                ))
                .build();

        KafkaSink<LOAN> loanSink = KafkaSink.<LOAN>builder()
                .setKafkaProducerConfig(producerConfig)
                .setRecordSerializer(loanSerializer)
                .setDeliveryGuarantee(DeliveryGuarantee.AT_LEAST_ONCE)
                .build();

        defineWorkflow(loanStream1, loanStream2)
                .sinkTo(loanSink)
                .name("loan_sink");

        env.setRestartStrategy(RestartStrategies.exponentialDelayRestart(
                Time.seconds(5),
                Time.minutes(1),
                2.0,
                Time.minutes(5),
                0.1

        ));

        env.execute("LOAN_TRANSFORMATION");
    }

    public static DataStream<LOAN> defineWorkflow(DataStream<LOAN_1> loanSource1, DataStream<LOAN_2> loanSource2) {
        DataStream<LOAN> loanSource1Stream = loanSource1.map(LOAN_1::toLOANFormat);

        DataStream<LOAN> loanSource2Stream = loanSource2.map(LOAN_2::toLOANFormat);

        return loanSource1Stream.union(loanSource2Stream);
    }
}

