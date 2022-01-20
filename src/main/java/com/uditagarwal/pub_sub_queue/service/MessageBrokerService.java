package com.uditagarwal.pub_sub_queue.service;

import com.uditagarwal.pub_sub_queue.handler.TopicHandler;
import com.uditagarwal.pub_sub_queue.model.Message;
import com.uditagarwal.pub_sub_queue.model.Topic;
import com.uditagarwal.pub_sub_queue.model.Subscriber;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MessageBrokerService {
    private final Map<String, TopicHandler> topicHandlersMap;

    public MessageBrokerService() {
        this.topicHandlersMap = new HashMap<>();
    }

    public Topic createTopic(@NonNull final String topicName) {
        final Topic topic = new Topic(topicName, UUID.randomUUID().toString());
        TopicHandler topicHandler = new TopicHandler(topic);
        topicHandlersMap.put(topic.getTopicId(), topicHandler);
        System.out.println("Created topic: " + topic.getTopicName());
        return topic;
    }

    public void subscribe(@NonNull final Subscriber subscriber, @NonNull final Topic topic) {
        topicHandlersMap.get(topic.getTopicId()).addSubscriber(subscriber);
        System.out.println(subscriber.getId() + " subscribed to topic: " + topic.getTopicName());
    }

    public void publish(@NonNull final Topic topic, @NonNull final Message message) {
        topic.addMessage(message);
        System.out.println(message.getMsg() + " published to topic: " + topic.getTopicName());
        new Thread(() -> topicHandlersMap.get(topic.getTopicId()).publish()).start();
    }

    public void resetOffset(@NonNull final Topic topic, @NonNull final Subscriber subscriber, @NonNull final Integer newOffset) {
        topicHandlersMap.get(topic.getTopicId()).resetOffset(subscriber, newOffset);
    }
}
