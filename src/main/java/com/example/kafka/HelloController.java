package com.example.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableKafka
public class HelloController {

    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    KafkaTemplate<String, Book> kafkaTemplate1;

    private static final String TOPIC = "first_topic";

    @GetMapping("/publish/{message}")
    public String publistMessage(@PathVariable("message") final String message){
        kafkaTemplate.send(TOPIC, message);
        return "Message Published successfully";
    }

    @PostMapping("/publish")
    public String publishJsonMessage(@RequestBody Book book){
        kafkaTemplate1.send(TOPIC, book);
        return "Message Published successfully JSON";
    }

    @KafkaListener(topics = TOPIC, groupId = "group_1")
    public void listen(String message){
        System.out.println("message1 = " + message);
    }
}
