package com.f5app.eventpublisher.mappers;

import com.f5app.eventpublisher.domain.Event;

public interface EventMap {

    Event mapToEvent(Object data);

}
