package xyz.leobellier.PortfolioManagement.streams;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {

    private static final String TOPIC = "portfolio-transaction";
    private final KafkaTemplate<String, String> template;
    public Producer(KafkaTemplate<String, String> template ){
        this.template = template;
    }


    public KafkaTemplate<String, String> getTemplate() {
        return template;
    }
}
