package com.vosmann.jis.queue;

import com.google.common.collect.Sets;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public class IdReceiver {

    private static final Logger LOG = LogManager.getLogger(IdReceiver.class);

    private final String queueName;
    private final ConnectionFactory factory;
    private final ExecutorService executor;

    private Connection connection;
    private Channel channel;
    private QueueingConsumer consumer;

    private Set<IdConsumer> idConsumers = Sets.newHashSet();

    public IdReceiver(final Queue queue) {
        queueName = queue.getName();
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        executor = Executors.newSingleThreadExecutor();
    }

    @PostConstruct
    public void init() {
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(queueName, false, false, false, null);

            consumer = new QueueingConsumer(channel);
            channel.basicConsume(queueName, true, consumer);

            LOG.error("Waiting for messages on queue {}.", queueName);

            executor.submit(() -> {
                while (true) {
                    try {
                        final QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                        LOG.error("Handling new delivery from queue {}.", queueName);

                        final String id = new String(delivery.getBody());
                        LOG.error("Received id {} from queue {}.", id, queueName);

                        for (final IdConsumer idConsumer : idConsumers) {
                            idConsumer.accept(id);
                        }
                    } catch (final InterruptedException e) {
                        LOG.error("Problem while receiving message.", e);
                    }
                }

            });
        } catch (final IOException e) {
            LOG.error("Could not initialize connection/channel for receiving IDs from queue.", e);
        }
    }

    public void addIdConsumer(final IdConsumer idConsumer) {
        idConsumers.add(idConsumer);
    }

}
