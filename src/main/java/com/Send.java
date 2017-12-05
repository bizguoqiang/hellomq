package com;

import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.*;

public class Send {
    private final static String EXCHANGE_NAME  = "helloworld";
    public static void main(String[] args) throws java.io.IOException,TimeoutException,InterruptedException{

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("120.79.41.93");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("111111");
        //创建一个新连接
        Connection connection = connectionFactory.newConnection();
        //创建一个通道
        Channel channel = connection.createChannel();
        //声明队列
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");

        for(int i=0; i<=1000;i++){
            String message = "Hello";
            channel.basicPublish("", EXCHANGE_NAME , null, message.getBytes("utf-8"));

        }

        //关闭连接和通道
        channel.close();
        connection.close();

    }
}
