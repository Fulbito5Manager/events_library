package com.f5app.eventpublisher.aspect.eventproducer.annotation;

import com.f5app.eventpublisher.domain.EventType;
import com.f5app.eventpublisher.domain.Topic;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventProducer {
    Topic topic();
    EventType eventType();
}
