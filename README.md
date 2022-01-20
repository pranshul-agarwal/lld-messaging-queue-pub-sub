# Low Level System Design - PubSub Messaging Queue


### Resources used
* https://www.youtube.com/watch?v=4BEzgPlLKTo  
* https://github.com/anomaly2104/low-level-design-messaging-messageBrokerService-pub-sub

### Concepts used
* Thread synchronization
* Inter thread communication

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
