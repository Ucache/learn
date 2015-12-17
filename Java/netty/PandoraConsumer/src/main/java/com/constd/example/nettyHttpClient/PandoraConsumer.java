/**
 * Created by qiniu on 15-12-17.
 */

package com.constd.example.nettyHttpClient;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.InterruptException;


public class PandoraConsumer {

    public static void main(String[] args){
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Arrays.asList("pandora"));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(10);
            try{
                Thread.sleep(1000);
            }catch (InterruptedException ex){
                continue;
            }
            for (ConsumerRecord<String, String> record : records)
                System.out.printf("offset = %d,value = %s\n", record.offset(), record.value());
        }
    }
}
