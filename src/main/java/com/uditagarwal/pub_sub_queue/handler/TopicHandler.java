package com.uditagarwal.pub_sub_queue.handler;

import com.uditagarwal.pub_sub_queue.model.Topic;
import com.uditagarwal.pub_sub_queue.model.Subscriber;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

public class TopicHandler {
    private final Topic topic;
    private final Map<String, SubscriberWorker> subscriberHandlerMap;

    public TopicHandler(@NonNull final Topic topic) {
        this.topic = topic;
        subscriberHandlerMap = new HashMap<>();
    }

    public void publish() {
        for (Subscriber subscriber : topic.getSubscribers()) {
            startSubscriberWorker(subscriber);
        }
    }

    public void addSubscriber(Subscriber subscriber) {
        topic.getSubscribers().add(subscriber);
    }

    public void resetOffset(Subscriber subscriber, @NonNull final Integer newOffset) {
        if(subscriber != null) {
            subscriber.getOffset().set(newOffset);
            System.out.println(subscriber.getId() + " offset reset to: " + newOffset);
            new Thread(() -> startSubscriberWorker(subscriber)).start();
        }
    }

    private void startSubscriberWorker(@NonNull final Subscriber subscriber) {
        final String subscriberId = subscriber.getId();
        if (!subscriberHandlerMap.containsKey(subscriberId)) {
            final SubscriberWorker subscriberWorker = new SubscriberWorker(topic, subscriber);
            subscriberHandlerMap.put(subscriberId, subscriberWorker);
            new Thread(subscriberWorker).start();
        }
        final SubscriberWorker subscriberWorker = subscriberHandlerMap.get(subscriberId);
        subscriberWorker.wakeUpIfNeeded();
    }
}
