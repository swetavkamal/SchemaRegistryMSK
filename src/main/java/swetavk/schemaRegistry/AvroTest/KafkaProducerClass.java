package swetavk.schemaRegistry.AvroTest;


import com.swetavk.avro.*;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import java.util.stream.IntStream;

public class KafkaProducerClass {
	
	 private static Producer<Long, Employee> createProducer() {
	        Properties props = new Properties();
	        
	        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "<your bootstrap servers");
	        props.put(ProducerConfig.CLIENT_ID_CONFIG, "AvroProducer");
	        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
	                LongSerializer.class.getName());

	        // Configure the KafkaAvroSerializer.
	       props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
	                KafkaAvroSerializer.class.getName());

	        // Schema Registry location.
	        props.put(KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG,
	                "http://localhost:8082");

	        return new KafkaProducer<>(props);
	    }
	 
	 private final static String TOPIC = "new-employees3";
	 
	 
	 public static void main(String args[])
	 {
		 System.out.println("In producer");
		 Producer<Long, Employee> producer = createProducer();
		 
		 Employee bob = Employee.newBuilder().setAge(35)
	                .setFirstName("Bob")
	                .setLastName("Jones")
	                .setPhoneNumber(
	                        PhoneNumber.newBuilder()
	                                .setAreaCode("301")
	                                .setCountryCode("1")
	                                .setPrefix("555")
	                                .setNumber("1234")
	                                .build())
	                .build();
		 
		 System.out.println(bob.toString());
		 IntStream.range(1, 100).forEach(index->{
	            producer.send(new ProducerRecord<>(TOPIC, 1L * index, bob));

	        });

	        producer.flush();
	        producer.close();
	 }

}
