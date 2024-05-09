package BO;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitmqConfig {
    private final static String EXCHANGE_NAME = "bo_exchange";
    private final static String ROUTING_KEY = "bo";

    public static void sendChanges(int boNumber) {
        ExportChangesToScript.exportProductSalesChangesToScript(boNumber);
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT, true);
            channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, SqlConfig.getScript().getBytes());
            System.out.println(SqlConfig.getScript());
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
