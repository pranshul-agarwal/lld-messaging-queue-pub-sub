package com.uditagarwal;

import com.uditagarwal.pub_sub_queue.public_interface.ISubscriber;
import com.uditagarwal.pub_sub_queue.model.Message;

public class SleepingSubscriber implements ISubscriber {
    private final String subscriberId;
    private final int sleepTimeInMillis;

    public SleepingSubscriber(String subscriberId, int sleepTimeInMillis) {
        this.subscriberId = subscriberId;
        this.sleepTimeInMillis = sleepTimeInMillis;
    }

    @Override
    public String getId() {
        return subscriberId;
    }

    @Override
    public void consume(Message message) throws InterruptedException {
        System.out.println("Subscriber: " + subscriberId + " started consuming: " + message.getMsg());
        Thread.sleep(sleepTimeInMillis);
        System.out.println("Subscriber: " + subscriberId + " done consuming: " + message.getMsg());
    }
}
