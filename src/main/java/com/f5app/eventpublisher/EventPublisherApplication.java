package com.f5app.eventpublisher;

import com.f5app.eventpublisher.domain.Event;
import com.f5app.eventpublisher.domain.Topic;
import com.f5app.eventpublisher.service.EventService;
import com.f5app.eventpublisher.service.impl.EventServiceImpl;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EventPublisherApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventPublisherApplication.class, args);
	}

}
