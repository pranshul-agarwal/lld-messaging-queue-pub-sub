package com.uditagarwal.pub_sub_queue.service;

import com.uditagarwal.pub_sub_queue.model.Message;

public interface SubscriberService {
    void consume(String subscriberId, int sleepTimeInMillis, Message message) throws InterruptedException;
}
