package com.f5app.eventpublisher.aspect.eventproducer;

import com.f5app.eventpublisher.aspect.eventproducer.annotation.EventProducer;
import com.f5app.eventpublisher.domain.Event;
import com.f5app.eventpublisher.domain.EventType;
import com.f5app.eventpublisher.domain.Topic;
import com.f5app.eventpublisher.service.EventService;
import com.f5app.eventpublisher.service.impl.EventMessageMapFactory;
import jakarta.annotation.PostConstruct;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class EventProducerAspect {

    @Pointcut("@annotation(com.f5app.eventpublisher.aspect.eventproducer.annotation.EventProducer)")
    public void sendEventPointcut() {
    }
    private final EventService eventService;
    private final EventMessageMapFactory mapFactory;

    @PostConstruct
    public void init() {
        System.out.println("EventProducerAspect initialized");
    }

    public EventProducerAspect(
            EventService eventService,
            EventMessageMapFactory mapFactory) {
        this.eventService = eventService;
        this.mapFactory = mapFactory;
    }

    @AfterReturning(pointcut = "sendEventPointcut()", returning = "result")
    public void afterMethodExecution(JoinPoint joinPoint, Object result) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        EventProducer annotation = method.getAnnotation(EventProducer.class);

        EventType eventType = annotation.eventType();
        Topic topic = annotation.topic();

        Event event = mapFactory.getMapper(eventType).mapToEvent(result);

        eventService.sendEvent(topic,event);
    }

}
