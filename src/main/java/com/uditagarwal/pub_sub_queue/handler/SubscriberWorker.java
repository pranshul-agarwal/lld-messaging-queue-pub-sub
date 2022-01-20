package com.uditagarwal.pub_sub_queue.handler;

import com.uditagarwal.pub_sub_queue.model.Message;
import com.uditagarwal.pub_sub_queue.model.Topic;
import com.uditagarwal.pub_sub_queue.model.Subscriber;
import com.uditagarwal.pub_sub_queue.service.SubscriberService;
import com.uditagarwal.pub_sub_queue.service.impl.SubscriberServiceImpl;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;

@Getter
public class SubscriberWorker implements Runnable {

    private final Topic topic;
    private final Subscriber subscriber;
    private static SubscriberService subscriberService;

    public SubscriberWorker(@NonNull final Topic topic, @NonNull final Subscriber subscriber) {
        this.topic = topic;
        this.subscriber = subscriber;
        if(this.subscriberService == null) {
            this.subscriberService = new SubscriberServiceImpl();
        }
    }

    @SneakyThrows
    @Override
    public void run() {
        synchronized (subscriber) {
            do {
                int curOffset = subscriber.getOffset().get();
                while (curOffset >= topic.getMessages().size()) {
                    subscriber.wait();
                }
                Message message = topic.getMessages().get(curOffset);
                subscriberService.consume(subscriber.getId(), subscriber.getSleepTimeInMillis(), message);

                // We cannot just increment here since subscriber offset can be reset while it is consuming. So, after
                // consuming we need to increase only if it was previous one.
                subscriber.getOffset().compareAndSet(curOffset, curOffset + 1);
            } while (true);
        }
    }

    public void wakeUpIfNeeded() {
        synchronized (subscriber) {
            subscriber.notify();
        }
    }
}
