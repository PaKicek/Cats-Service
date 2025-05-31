package org.pakicek.webgateway.Configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic createCatTopic() {
        return new NewTopic("cat-save-topic", 1, (short) 1);
    }
    @Bean
    public NewTopic createCatTopicReplies() {
        return new NewTopic("cat-save-topic.replies", 1, (short) 1);
    }
    @Bean
    public NewTopic deleteByIdCatTopic() {
        return new NewTopic("cat-deletebyid-topic", 1, (short) 1);
    }
    @Bean
    public NewTopic deleteByIdCatTopicReplies() {
        return new NewTopic("cat-deletebyid-topic.replies", 1, (short) 1);
    }
    @Bean
    public NewTopic deleteAllCatsTopic() {
        return new NewTopic("cat-deleteall-topic", 1, (short) 1);
    }
    @Bean
    public NewTopic deleteAllCatsTopicReplies() {
        return new NewTopic("cat-deleteall-topic.replies", 1, (short) 1);
    }
    @Bean
    public NewTopic updateCatTopic() {
        return new NewTopic("cat-update-topic", 1, (short) 1);
    }
    @Bean
    public NewTopic updateCatTopicReplies() {
        return new NewTopic("cat-update-topic.replies", 1, (short) 1);
    }
    @Bean
    public NewTopic createPersonTopic() {
        return new NewTopic("person-save-topic", 1, (short) 1);
    }
    @Bean
    public NewTopic createPersonTopicReplies() {
        return new NewTopic("person-save-topic.replies", 1, (short) 1);
    }
    @Bean
    public NewTopic deleteByIdPersonTopic() {
        return new NewTopic("person-deletebyid-topic", 1, (short) 1);
    }
    @Bean
    public NewTopic deleteByIdPersonTopicReplies() {
        return new NewTopic("person-deletebyid-topic.replies", 1, (short) 1);
    }
    @Bean
    public NewTopic deleteAllPersonTopic() {
        return new NewTopic("person-deleteall-topic", 1, (short) 1);
    }
    @Bean
    public NewTopic deleteAllPersonTopicReplies() {
        return new NewTopic("person-deleteall-topic.replies", 1, (short) 1);
    }
    @Bean
    public NewTopic updatePersonTopic() {
        return new NewTopic("person-update-topic", 1, (short) 1);
    }
    @Bean
    public NewTopic updatePersonTopicReplies() {
        return new NewTopic("person-update-topic.replies", 1, (short) 1);
    }
}
