package com.uditagarwal.pub_sub_queue.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
@AllArgsConstructor
public class Subscriber {
    private final AtomicInteger offset;
    private final String id;
    private final int sleepTimeInMillis;

    public Subscriber(@NonNull final String subscriberId, final int sleepTimeInMillis) {
        this.id = subscriberId;
        this.sleepTimeInMillis = sleepTimeInMillis;
        this.offset = new AtomicInteger(0);
    }
}
