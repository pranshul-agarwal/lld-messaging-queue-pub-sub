# Low Level System Design - PubSub Messaging Queue


### Resources used
* https://www.youtube.com/watch?v=4BEzgPlLKTo  
* https://github.com/anomaly2104/low-level-design-messaging-messageBroker-pub-sub

### Concepts used
* Thread synchronization
* Inter thread communication

```
Main   1..1   MessageBroker   1..n   TopicHandler   1..n    SubscriberHandler   1..1   TopicSubscriber   1..1   ISubscriber  
Topic  1..n   TopicSubscriber   1..1   ISubscriber
Topic  1..1   TopicHandler


TopicSubscriber
- Subscriber
- Offset

Topic
- Message
- List<TopicSubscriber>

SleepingSubscriber implements ISubscriber
- subscriberId
- sleepTimeInMillis
- consume(message)              // consumes message and sleeps Thread.sleep(sleepTimeInMillis)

SubscriberHandler
- Topic
- TopicSubscriber
- synchronized wakeUpIfNeeded()     // check the current offset in topicsubcriber. If already consumed, wait() else call SleepingSubscriber.consume() and increase offset

TopicHandler
- Topic
- Map<String, SubscriberHandler>      // while creating new subscriber, create in a new thread  => new Thread(SubscriberHandler).start()
- publishMessage()  // for all subscribers of this topic, call subscriberWorker.wakeUpIfNeeded()
- addSubscriber()
- resetOffset.            // reset offset in topic->topic subscriber and  call subscriberWorker.wakeUpIfNeeded()

MessageBroker
- publishMessage(topicId)    // add message to topic and call publish of topic handler in new thread => new Thread(() -> topicHandler.publish()).start()
- subscribe()
- createTopic()
- resetOffset                          // call topichandler reset offset

```

### Video Explanation
https://youtu.be/4BEzgPlLKTo

### Problem statement
[Check here](problem-statment.md)

### Project requirements
* JDK 1.8
* Maven

## APIs supported in queue
* createTopic(topicName) -> topicId
* subscribe(topicId, subscriber) -> boolean
* publish(topicId, message) -> boolean
* readOffset(topidId, subscriber, offset) -> boolean
