package HO;

import SQL.MySQLConnection;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeoutException;
import java.sql.Connection;

public class RabbitmqConfig {
    private static Statement statement;
    private final static String QUEUE_NAME = "bo_queue";
    private final static String EXCHANGE_NAME = "bo_exchange";
    private final static String ROUTING_KEY = "bo";

    public static void main(String[] args) throws Exception {

        // Establish MySQL connection
       try {
            Connection connection = MySQLConnection.connect("HO");
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        // Establish connection to RabbitMQ
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        com.rabbitmq.client.Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);

        // Create a consumer to receive messages from RabbitMQ
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("Received script: " + message);

            // Split the script into individual queries
            String[] queries = message.split(";");
            for (String query : queries) {
                try {
                    // Execute the query on the MySQL database
                    statement.executeUpdate(query);
                    System.out.println("Query executed successfully: " + query);
                } catch (SQLException e) {
                    System.out.println("Error executing query: " + query);
                    e.printStackTrace();
                }
            }
        };

        // Start consuming messages from the queue
        channel.basicConsume(QUEUE_NAME, false, deliverCallback, consumerTag -> { });

        System.out.println("Waiting for data changes from Branch Office...");
    }

}

