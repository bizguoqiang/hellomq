package com;
import com.rabbitmq.client.*;
import java.util.concurrent.TimeoutException;

public class Receive {
    private final static String EXCHANGE_NAME  = "hello";
    public static void main(String[] args) throws java.io.IOException,TimeoutException,InterruptedException{

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("120.79.41.93");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("111111");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        String queueName =channel.queueDeclare().getQueue();
        channel.queueBind(queueName,EXCHANGE_NAME,"");
        System.out.println("Waiting for messages. To exit press CTRL+C");

        //创建队列消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //指定消费队列
        channel.basicConsume(queueName, true, consumer);
        while (true) {
            //nextDelivery是一个阻塞方法（内部实现其实是阻塞队列的take方法）
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String news = new String(delivery.getBody());
            System.out.println("Received '" + news + "'");
        }

    }
}
