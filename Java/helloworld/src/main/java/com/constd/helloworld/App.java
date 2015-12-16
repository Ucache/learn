package com.constd.helloworld;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.*;
import org.apache.kafka.common.errors.*;
import org.apache.kafka.common.serialization.*;

import java.util.Arrays;
import java.util.Properties;
import java.util.Date;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        PandoraProducer p = new PandoraProducer();
        p.Send();
    }

    public static void runConsumer(){
        Properties props = new Properties();
        props.put("bootstrap.servers","192.168.201.118:9092");
        props.put("group.id","pandora");
        props.put("enable.auto.commit","true");
        props.put("auto.commit.interval.ms","1000");
        props.put("session.timeout.ms","30000");
        props.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String,String> consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Arrays.asList("foo","bar"));

        while (true){
            ConsumerRecords<String,String> records = consumer.poll(100);
            for(ConsumerRecord<String,String> record:records)
                System.out.printf("offset = %d, key=%s, value=%s \n",record.offset(),record.key(),record.value());
        }
    }
    public static void runProducer(){
        Properties props = new Properties();
        props.put("bootstrap.servers","192.168.201.118:9092");
        props.put("acks","all");
        props.put("retries",0);
        props.put("batch.size",16*1024);
        props.put("linger.ms",1);
        props.put("buffer.memory",33554432);
        props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        for(int i=0;i<10;i++) {
            System.out.println(new Date());

            producer.send(new ProducerRecord<String, String>("pandora", Integer.toString(i), Integer.toString(i)));
        }
        producer.close();
    }
}

