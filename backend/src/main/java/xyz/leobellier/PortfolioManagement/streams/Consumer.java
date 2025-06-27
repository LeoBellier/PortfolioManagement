package xyz.leobellier.PortfolioManagement.streams;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {
    @KafkaListener(topics = "portfolio-transaction", groupId = "portfolio-group")
    public void consumed(String message){
        System.out.println("message with tokens" + message);
    }
}
