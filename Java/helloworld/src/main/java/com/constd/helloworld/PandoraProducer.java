package com.constd.helloworld;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Date;
import java.util.Properties;

/**
 * Created by qiniu on 15-12-16.
 */
public class PandoraProducer {

    private Producer<String, String> producer;
    public PandoraProducer(){
        Properties props = new Properties();
        props.put("bootstrap.servers","192.168.201.118:9092");
        props.put("acks","all");
        props.put("retries",0);
        props.put("batch.size",16*1024);
        props.put("linger.ms",1);
        props.put("buffer.memory",33554432);
        props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<String, String>(props);

    }

    public void Send(){

        for(int i=0;i<10;i++) {
            System.out.println(i +":" + (new Date()).toString());
            producer.send(new ProducerRecord<String, String>("pandora", (new Date()).toString(), (new Date()).toString()));
        }
        producer.close();
    }


}
