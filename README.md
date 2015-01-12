# ofs.messaging

This is an abstraction layer that uses RabbitMQ as the broker. Clients (Producers) can register for an event and then publish messages that belong to that Event.
Clients (Consumers) can subscribe to an event and provide a callback and when any message is available, it will be sent to the callback interface registered

