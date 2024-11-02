package com.f5app.eventpublisher.service;

import com.f5app.eventpublisher.domain.Event;
import com.f5app.eventpublisher.domain.Topic;

public interface EventService {

    String sendEvent(Topic topic, Event eventData);

}