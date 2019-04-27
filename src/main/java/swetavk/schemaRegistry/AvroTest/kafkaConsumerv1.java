package swetavk.schemaRegistry.AvroTest;


import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;

import java.util.Collections;
import java.util.Properties;
import java.util.stream.IntStream;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import com.swetavk.avro.*;

public class kafkaConsumerv1 {

	private final static String BOOTSTRAP_SERVERS = "<Your bootstrap servers>";
	private final static String TOPIC = "new-employees3";

	private static Consumer<Long, Employee> createConsumer() {
		Properties props = new Properties();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "KafkaExampleAvroConsumer");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());

		// Use Kafka Avro Deserializer.
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName()); // <----------------------

		// Use Specific Record or else you get Avro GenericRecord.
		props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, "true");

		// Schema registry location.
		props.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8082"); // <----- Run Schema
																									// Registry on 8081

		return new KafkaConsumer<>(props);

	}
	
	public static void main(String args[])
	{
		System.out.println("IN consumer");
		 final Consumer<Long, Employee> consumer = createConsumer();
	        consumer.subscribe(Collections.singletonList(TOPIC));

	        IntStream.range(1, 100).forEach(index -> {

	            final ConsumerRecords<Long, Employee> records =
	                    consumer.poll(1000);

	            if (records.count() == 0) {
	                System.out.println("None found");
	            } else records.forEach(record -> {

	                Employee employeeRecord = record.value();

	                System.out.printf("%s %d %d %s \n", record.topic(),
	                        record.partition(), record.offset(), employeeRecord);
	            });
	        });
	}
}

