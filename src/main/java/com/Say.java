package com;


import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Scanner;

public class Say {
    public static void say() throws Exception{
        String EXCHANGE_NAME = "hello";
        //创建连接
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("120.79.41.93");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("111111");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        //声明转发器和类型
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        System.out.println("欢迎您，请输入您的昵称：");
        String userName = new Scanner(System.in).next();
        while(true){
            String news = new Scanner(System.in).next();
            if(news.equals("out")){
                    connection.close();
                    channel.close();
                    return;
            }else{
                news = userName + ":" + news;
                channel.basicPublish(EXCHANGE_NAME,"",null,news.getBytes());
            }
        }
    }
    public static void user() throws Exception{
        String EXCHANGE_NAME = "hello";
        //创建连接
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("120.79.41.93");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("111111");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        //声明转发器和类型
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        String queue = channel.queueDeclare().getQueue();//创建队列
        channel.queueBind(queue,EXCHANGE_NAME,"");

        System.out.println(" [*] Waiting for messages");

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException{
                String message = new String(body, "UTF-8");
                System.out.println(message);
            }
        };
        channel.basicConsume(queue, true, consumer);
    }


}
