
# Events Library

A library designed to streamline event dispatching in distributed systems based on Kafka. It offers a straightforward, boilerplate-free approach to dispatching events through a message queue or event bus.

## Table of Contents

1. [Description](#description)
2. [Installation](#installation)
3. [Usage](#usage)
4. [Configuration](#configuration)
5. [Contributing](#contributing)
6. [License](#license)

---

### 1. Description

The **Events Library** provides an asynchronous solution for dispatching events between microservices. By using annotations, developers can define event types and topics, decoupling services from the event bus.

### 2. Installation

To install this library, add the following dependency to your project. For example, if using **Maven**:

```xml
<dependency>
    <groupId>com.f5app</groupId>
    <artifactId>eventpublisher</artifactId>
    <version>1.0.0</version>
</dependency>
```

Or with **Gradle**:

```groovy
implementation 'com.f5app:eventpublisher:1.0.0'
```

### 3. Usage

1. **Event Configuration and Mapping**: For each event type, you need to create a `mapper` implementing `EventMap`, transforming the target object into a suitable event. These `mappers` are registered centrally in the `EventMessageMapFactory` configuration.

#### Event Configuration Example

Here’s an example configuration class, `EventsConfig`, which sets up `EventMessageMapFactory` with the necessary `mappers`:

```java
package com.api.player.events.config;

import com.api.player.events.mapper.PlayerCreatedEventMap;
import com.f5app.eventpublisher.domain.EventType;
import com.f5app.eventpublisher.mappers.EventMap;
import com.f5app.eventpublisher.service.impl.EventMessageMapFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class EventsConfig {

    private final PlayerCreatedEventMap playerCreatedEventMap;

    public EventsConfig(PlayerCreatedEventMap playerCreatedEventMap) {
        this.playerCreatedEventMap = playerCreatedEventMap;
    }

    @Bean
    public EventMessageMapFactory eventMessageMapFactory() {
        Map<EventType, EventMap> mappers = new HashMap<>();
        mappers.put(EventType.PLAYER_CREATED, playerCreatedEventMap);
        return new EventMessageMapFactory(mappers);
    }
}
```

2. **Mapper Implementation**: Each event type requires its own `mapper` implementing `EventMap`, which maps the target object to an `Event`.

#### Mapper Implementation Example

Below is an example of `PlayerCreatedEventMap`, which converts a `Player` object into a `PLAYER_CREATED` event:

```java
package com.api.player.events.mapper;

import com.api.player.domain.Player;
import com.f5app.eventpublisher.domain.Event;
import com.f5app.eventpublisher.domain.EventType;
import com.f5app.eventpublisher.mappers.EventMap;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PlayerCreatedEventMap implements EventMap {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Event mapToEvent(Object data) {
        Event event = new Event();
        Player playerCreated = (Player) data;
        String playerCreatedEventData = getPlayerCreatedEventData(playerCreated);
        event.setEventType(EventType.PLAYER_CREATED);
        event.setData(playerCreatedEventData);
        return event;
    }

    private String getPlayerCreatedEventData(Player playerCreated) {
        Map<String, Object> eventPayload = new HashMap<>();
        eventPayload.put("player_id", playerCreated.getId());
        eventPayload.put("player_name", playerCreated.getName());
        eventPayload.put("player_email", playerCreated.getEmail());
        eventPayload.put("player_phone_number", "11522129202");
        try {
            return objectMapper.writeValueAsString(eventPayload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
```

3. **Method Annotation**: Annotate methods that need to dispatch events, and ensure that the method returns the object intended for interception.

```java
@SendEvent(topic = Topics.MATCH_FINISHED, eventType = EventType.UPDATE)
public Match updateMatch(Long matchId) {
    Match match = matchService.finishMatch(matchId);
    return match; // Returns the object for interception and dispatch
}
```

### 4. Configuration

Library configuration is done through environment variables or properties in the `application.properties` file, defining Kafka settings and other necessary configurations.

```properties
kafka.bootstrap.servers=localhost:9092
kafka.security.protocol=SSL
kafka.ssl.truststore.location=/path/to/truststore.jks
kafka.ssl.truststore.password=password
```

### 5. Contributing

To contribute to this project:

1. **Fork** this repository.
2. Create a new branch (`git checkout -b feature/new-feature`).
3. Make your changes and commit them (`git commit -m 'Add new feature'`).
4. Push to the branch (`git push origin feature/new-feature`).
5. Open a **Pull Request**.

### 6. License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

### Changelog

#### Version 1.0.0
- Initial release with basic event dispatching functionality and mappers.
