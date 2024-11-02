package com.f5app.eventpublisher.service.impl;

import com.f5app.eventpublisher.domain.EventType;
import com.f5app.eventpublisher.mappers.EventMap;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EventMessageMapFactory {

    private final Map<EventType, EventMap> mappers;

    public EventMessageMapFactory(Map<EventType, EventMap> mappers) {
        this.mappers = mappers;
    }


    public EventMap getMapper(EventType eventType) {
        return mappers.get(eventType);
    }

}
