package com.uditagarwal.pub_sub_queue.service.impl;

import com.uditagarwal.pub_sub_queue.service.SubscriberService;
import com.uditagarwal.pub_sub_queue.model.Message;

import java.time.LocalDateTime;

public class SubscriberServiceImpl implements SubscriberService {
    @Override
    public void consume(String subscriberId, int sleepTimeInMillis, Message message) throws InterruptedException {
        System.out.println(LocalDateTime.now().getSecond() + " Subscriber: " + subscriberId + " started consuming: " + message.getMsg());
        Thread.sleep(sleepTimeInMillis);
        System.out.println(LocalDateTime.now().getSecond() + " Subscriber: " + subscriberId + " done consuming: " + message.getMsg());
    }
}
