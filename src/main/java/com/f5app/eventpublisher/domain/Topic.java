package com.f5app.eventpublisher.domain;

public enum Topic {

    MATCH_SERVICE_TOPIC("match_service_topic"),
    PLAYER_SERVICE_TOPIC("player_service_topic");

    private final String topicValue;

    Topic(String topicValue) {
        this.topicValue = topicValue;
    }

    public String getTopicValue() {
        return topicValue;
    }
}


