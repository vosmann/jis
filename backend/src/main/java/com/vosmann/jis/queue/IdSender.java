package com.vosmann.jis.queue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class IdSender {

    private static final Logger LOG = LogManager.getLogger(IdSender.class);

    private final String queueName;
    private final ConnectionFactory factory;

    public IdSender(final Queue queue) {
        queueName = queue.getName();
        factory = new ConnectionFactory();
        factory.setHost("localhost");
    }

    public void send(String id) {
        LOG.error("Sending id {} to queue {}.", id, queueName);
        // Depending on the case, could maybe keep single (or pool of) connection and channel open for a longer time.
        try {
            final Connection connection = factory.newConnection();
            final Channel channel = connection.createChannel();

            channel.basicPublish("", queueName, null, id.getBytes()); // A beautiful method signature.

            channel.close();
            connection.close();
        } catch (final IOException e) {
            LOG.error("Sending id {} to queue {} failed.", id, queueName, e);
        }
    }

}
