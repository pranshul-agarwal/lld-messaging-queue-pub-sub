package com.uditagarwal.pub_sub_queue.handler;

import com.uditagarwal.pub_sub_queue.model.Topic;
import com.uditagarwal.pub_sub_queue.model.TopicSubscriber;
import com.uditagarwal.pub_sub_queue.public_interface.ISubscriber;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

public class TopicHandler {
    private final Topic topic;
    private final Map<String, SubscriberWorker> subscriberWorkers;

    public TopicHandler(@NonNull final Topic topic) {
        this.topic = topic;
        subscriberWorkers = new HashMap<>();
    }

    public void publish() {
        for (TopicSubscriber topicSubscriber : topic.getSubscribers()) {
            startSubscriberWorker(topicSubscriber);
        }
    }

    public void addSubscriber(TopicSubscriber subscriber) {
        topic.getSubscribers().add(subscriber);
    }

    public void resetOffset(ISubscriber subscriber, @NonNull final Integer newOffset) {
        TopicSubscriber topicSubscriber = topic.getSubscribers().stream()
                .filter(ts -> ts.getSubscriber().equals(subscriber))
                .findFirst()
                .orElse(null);
        if(topicSubscriber != null) {
            topicSubscriber.getOffset().set(newOffset);
            System.out.println(topicSubscriber.getSubscriber().getId() + " offset reset to: " + newOffset);
            new Thread(() -> startSubscriberWorker(topicSubscriber)).start();
        }
    }

    private void startSubscriberWorker(@NonNull final TopicSubscriber topicSubscriber) {
        final String subscriberId = topicSubscriber.getSubscriber().getId();
        if (!subscriberWorkers.containsKey(subscriberId)) {
            final SubscriberWorker subscriberWorker = new SubscriberWorker(topic, topicSubscriber);
            subscriberWorkers.put(subscriberId, subscriberWorker);
            new Thread(subscriberWorker).start();
        }
        final SubscriberWorker subscriberWorker = subscriberWorkers.get(subscriberId);
        subscriberWorker.wakeUpIfNeeded();
    }
}
