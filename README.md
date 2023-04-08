# SpringGPT

## What is it about
This is an application under Spring Boot Framework. It has two main classes:
- Consumer: Receives a response and sends a query
- Runner: Receives a request and sends a HTTP Post event to the Chat GPT application. With the response, it is posted into a messaging queue.
- The communication between Consumer and Runner is done with RabbitMQ messaging broker, which configuration can be seen in the MessagingConfig class.

## How to make it work
- Make sure you have open ai installed in your computer
- Make sure you have rabbitmq installed in your computer
- Update application.properties with your Open AI Key
## Future implementations
- Put the application on Docker container
- Develop a Front-End and make the use send the queries through it
- Adding Java Doc