package com.constd.example.nettyHttpServer;

/**
 * Created by lamo on 15/12/16.
 */

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.*;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.errors.*;

import java.util.Properties;

public class PandoraProducer {

    public Producer<String, String> producer;

    public PandoraProducer(String server){
        Properties props = new Properties();
        props.put("bootstrap.servers","192.168.201.118:9092");
        props.put("acks","all");
        props.put("retries",0);
        props.put("batch.size",16384);
        props.put("linger.ms",1);
        props.put("buffer.memory",33554432);
        props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        this.producer = new KafkaProducer<String,String>(props);
    }

    public  void Send(String topic, String msg){
        System.out.println("send msg:" + msg);
        this.producer.send(new ProducerRecord<String, String>("pandora","msg","msg"));
        this.producer.close();
    }
}
