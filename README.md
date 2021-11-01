# Low Level System Design - PubSub Messaging Queue


### Resources used
* https://www.youtube.com/watch?v=4BEzgPlLKTo  
* https://github.com/anomaly2104/low-level-design-messaging-messageBroker-pub-sub

### Concepts used
* Thread synchronization
* Inter thread communication

```
Main   1..1   MessageBroker   1..n   TopicHandler   1..n    SubscriberWorker   1..1   TopicSubscriber   1..1   ISubscriber
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
