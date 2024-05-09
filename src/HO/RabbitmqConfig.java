package HO;

import SQL.MySQLConnection;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;

public class RabbitmqConfig {
    private final static String QUEUE_NAME = "bo_queue";
    private final static String EXCHANGE_NAME = "bo_exchange";
    private final static String ROUTING_KEY = "bo";

    public static void updateHo() throws Exception {

        // Establish MySQL connection
        Connection dbConnection = MySQLConnection.connect("HO");

        // Establish connection to RabbitMQ
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        com.rabbitmq.client.Connection mqConnection = factory.newConnection();
        Channel channel = mqConnection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT, true);
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
        System.out.println("Waiting for data changes from Branch Office...");

        // Create a consumer to receive messages from RabbitMQ
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println("Received script: " + message);

            // Split the script into individual queries
            String[] queries = message.split(";");
            for (String query : queries) {
                if (query.trim().isEmpty()) continue; // Skip empty queries
                try (java.sql.Statement statement = dbConnection.createStatement()) {
                    statement.executeUpdate(query);
                    System.out.println("Query executed successfully: " + query);
                } catch (SQLException e) {
                    System.out.println("Error executing query: " + query);
                    e.printStackTrace();
                }
            }
        };

        // Start consuming messages from the queue
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });
    }
}
