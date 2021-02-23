package com.mzy.config;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @Author Jack Miao
 * @date 2021/1/15 14:07
 * @desc
 */
@Configuration
public class RabbitConfig {

    @Value("${spring.rabbitmq.accKey}")
    private String accKey;
    @Value("${spring.rabbitmq.secKey}")
    private String secKey;
    @Value("${spring.rabbitmq.instanceId}")
    private String instanceId;
    @Value("${spring.rabbitmq.exchange.name}")
    private String exchangeName;
    @Value("${spring.rabbitmq.queue.name}")
    private String queueName;
    @Value("${spring.rabbitmq.routing.key}")
    private String routingKey;
    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;

    @Bean
    @Primary
    public ConnectionFactory getConnectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        // 设置接入点
        factory.setHost(host);
        // 获取动态用户名密码
        factory.setCredentialsProvider(new AliyunCredentialsProvider(accKey, secKey, instanceId));
        // 连接自动恢复
        factory.setAutomaticRecoveryEnabled(true);
        // 网络恢复间隔
        factory.setNetworkRecoveryInterval(5000);
        // 设置Vhost名称
        factory.setVirtualHost(virtualHost);
        // 默认端口，非加密端口5672，加密端口5671
        factory.setPort(5672);
        // 基于网络环境合理设置超时时间。
        factory.setConnectionTimeout(30 * 1000);
        factory.setHandshakeTimeout(30 * 1000);
        factory.setShutdownTimeout(0);
        return factory;
    }



   /* @Bean
    public ConnectionFactory getConnectionFactory(){
        com.rabbitmq.client.ConnectionFactory rabbitConnectionFactory =
                new com.rabbitmq.client.ConnectionFactory();
        rabbitConnectionFactory.setHost(host);
        rabbitConnectionFactory.setPort(5672);
        rabbitConnectionFactory.setVirtualHost(virtualHost);

        AliyunCredentialsProvider credentialsProvider = new AliyunCredentialsProvider(accKey, secKey, instanceId);
        rabbitConnectionFactory.setCredentialsProvider(credentialsProvider);
        rabbitConnectionFactory.setAutomaticRecoveryEnabled(true);
        rabbitConnectionFactory.setNetworkRecoveryInterval(5000);
        ConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitConnectionFactory);
        ((CachingConnectionFactory)connectionFactory).setPublisherConfirms(true);
        ((CachingConnectionFactory)connectionFactory).setPublisherReturns(true);
        return connectionFactory;
    }



    *//** 申明队列
     *
     * @return
     */

    /*@Bean
    public Queue queue() {

        return new Queue(queueName, true, false, false);
    }

    @Bean
    public Exchange exchange() {
        return new DirectExchange(exchangeName, true, false);
    }


    *//**
     * 申明绑定
     * @return
     *//*
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with(routingKey).noargs();
    }
*/
}
