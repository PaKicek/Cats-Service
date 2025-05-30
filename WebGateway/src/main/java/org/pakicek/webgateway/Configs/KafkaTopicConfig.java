package org.pakicek.webgateway.Configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic createCatTopic() {
        return new NewTopic("create-cat-topic", 1, (short) 1);
    }
    @Bean
    public NewTopic updateCatTopic() {
        return new NewTopic("update-cat-topic", 1, (short) 1);
    }
    @Bean
    public NewTopic deleteByIdCatTopic() {
        return new NewTopic("delete-by-id-cat-topic", 1, (short) 1);
    }
    @Bean
    public NewTopic deleteAllCatsTopic() {
        return new NewTopic("delete-all-cats-topic", 1, (short) 1);
    }
    @Bean
    public NewTopic createPersonTopic() {
        return new NewTopic("create-person-topic", 1, (short) 1);
    }
    @Bean
    public NewTopic updatePersonTopic() {
        return new NewTopic("update-person-topic", 1, (short) 1);
    }
    @Bean
    public NewTopic deleteByIdPersonTopic() {
        return new NewTopic("delete-by-id-person-topic", 1, (short) 1);
    }
    @Bean
    public NewTopic deleteAllPersonsTopic() {
        return new NewTopic("delete-all-persons-topic", 1, (short) 1);
    }
}
