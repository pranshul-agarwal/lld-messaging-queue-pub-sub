package com.uditagarwal;

import com.uditagarwal.pub_sub_queue.model.Subscriber;
import com.uditagarwal.pub_sub_queue.service.MessageBrokerService;
import com.uditagarwal.pub_sub_queue.model.Message;
import com.uditagarwal.pub_sub_queue.model.Topic;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        final MessageBrokerService messageBrokerService = new MessageBrokerService();
        final Topic topic1 = messageBrokerService.createTopic("t1");
        final Topic topic2 = messageBrokerService.createTopic("t2");
        final Subscriber sub1 = new Subscriber("sub1", 10000);
        final Subscriber sub2 = new Subscriber("sub2", 10000);
        messageBrokerService.subscribe(sub1, topic1);
        messageBrokerService.subscribe(sub2, topic1);

        final Subscriber sub3 = new Subscriber("sub3", 5000);
        messageBrokerService.subscribe(sub3, topic2);

        messageBrokerService.publish(topic1, new Message("m1"));
        messageBrokerService.publish(topic1, new Message("m2"));

        messageBrokerService.publish(topic2, new Message("m3"));

        Thread.sleep(15000);
        messageBrokerService.publish(topic2, new Message("m4"));
        messageBrokerService.publish(topic1, new Message("m5"));

        messageBrokerService.resetOffset(topic1, sub1, 0);
    }
}
