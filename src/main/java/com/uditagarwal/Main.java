package com.uditagarwal;

import com.uditagarwal.pub_sub_queue.public_interface.MessageBroker;
import com.uditagarwal.pub_sub_queue.model.Message;
import com.uditagarwal.pub_sub_queue.model.Topic;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        final MessageBroker messageBroker = new MessageBroker();
        final Topic topic1 = messageBroker.createTopic("t1");
        final Topic topic2 = messageBroker.createTopic("t2");
        final SleepingSubscriber sub1 = new SleepingSubscriber("sub1", 10000);
        final SleepingSubscriber sub2 = new SleepingSubscriber("sub2", 10000);
        messageBroker.subscribe(sub1, topic1);
        messageBroker.subscribe(sub2, topic1);

        final SleepingSubscriber sub3 = new SleepingSubscriber("sub3", 5000);
        messageBroker.subscribe(sub3, topic2);

        messageBroker.publish(topic1, new Message("m1"));
        messageBroker.publish(topic1, new Message("m2"));

        messageBroker.publish(topic2, new Message("m3"));

        Thread.sleep(15000);
        messageBroker.publish(topic2, new Message("m4"));
        messageBroker.publish(topic1, new Message("m5"));

        messageBroker.resetOffset(topic1, sub1, 0);
    }
}
