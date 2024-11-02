package com.f5app.eventpublisher.service.impl;

import com.f5app.eventpublisher.domain.Event;
import com.f5app.eventpublisher.domain.Topic;
import com.f5app.eventpublisher.service.EventService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Slf4j
public class EventServiceImpl implements EventService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public EventServiceImpl(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public String sendEvent(Topic topic, Event eventData){
        log.info(
                "Sending message to topic={} with data={}",
                topic,
                eventData
        );
        String eventString;
        try {
            eventString = objectMapper.writeValueAsString(eventData);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        kafkaTemplate.send(topic.getTopicValue(),eventString);
        return "";
    }
}
