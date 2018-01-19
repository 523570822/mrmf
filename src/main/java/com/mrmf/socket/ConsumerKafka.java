package com.mrmf.socket;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;


public class ConsumerKafka extends Thread {

    private KafkaConsumer<String,String> consumer;
    private String topic = "kafkaTopic";
    private Session session;
    public ConsumerKafka(Session session){
        session=session;
    }
    public ConsumerKafka(){

    }

    @Override
    public void run(){
        //加载kafka消费者参数
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "ytna");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "15000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        //创建消费者对象
        consumer = new KafkaConsumer<String,String>(props);
        consumer.subscribe(Arrays.asList(this.topic));
        //死循环，持续消费kafka
        while (true){
            System.out.println("无限循环开始");
            try {
                //消费数据，并设置超时时间
                ConsumerRecords<String, String> records = consumer.poll(100);

                //Consumer message
                for (ConsumerRecord<String, String> record : records) {


                    for (Map.Entry<String, WebSocket> entry :WebSocket.webSocketSet.entrySet()) {
                        System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());


                            System.out.println("record.value()："+record.value());
                            entry.getValue().sendMessage(record.value());

                    }
                    //Send message to every client
                  /*  for (WebSocket webSocket :WebSocket.webSocketSet){
                        System.out.println("record.value()："+record.value());
                        webSocket.sendMessage(record.value());
                    }*/
                }
            }catch (IOException e){
                System.out.println(e.getMessage());
                continue;
            }
        }
    }

    public void close() {
        try {
            consumer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //供测试用，若通过tomcat启动需通过其他方法启动线程
    public static void main(String[] args){
        ConsumerKafka consumerKafka = new ConsumerKafka();
        consumerKafka.start();
    }
}
