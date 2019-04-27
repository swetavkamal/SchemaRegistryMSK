# SchemaRegistryMSK
This project has sample code on how to use schema registry with MSK

1. Schema registry will use Confluent open source schema registry project and it will be talking to MSK(Managed streaming Kafka) cluster.

	1.1 Launch a MSK cluster
	1.2 Create a client machine in the same VPC as MSK cluster and keep the security group such that MSK cluster and client machine can talk to each other. you can follow the below link to set up client machine :
		https://docs.aws.amazon.com/msk/latest/developerguide/create-client-machine.html
	1.3 Download Confluent from https://www.confluent.io/previous-versions/ [ I used 5.1.2 with MSK 1.1.1 ] and untar it or even you can install confluent hub on your client machine.

2. Once you have the client machine ready and confluent downloaded and extracted, Go to the schema-registry settings file.
	You would find file "schema-registry.properties" at location "confluent-5.1.2/etc/schema-registry"
	2.1 Make below changes : 
	listeners=http://0.0.0.0:8082 [ You can take port of your choice where your schema registery listners will listen ]
	kafkastore.connection.url= <Your-zookeeper-servers>
	kafkastore.bootstrap.servers=PLAINTEXT://<your-bootstrap-server>

3. Once you have the settings ready, you need to start the schema-registry server. Go to confluent-5.1.2/bin and run command : ./schema-registry-start ../etc/schema-registry/schema-registry.properties 
	You would see something similar to below :
		 INFO Server started, listening for requests... (io.confluent.kafka.schemaregistry.rest.SchemaRegistryMain:44)
     
4. Now import the current project in your eclipse and build it, you would see schem files generated. Make changes as per your requirement.

5. Once, every thing is ready, export each version of the producers and consumers seperately in your client machine and start running:

java -jar producer1.jar
java -jar consumer1.jar

jave -jar producer2.jar
java -jar consumer2.jar
