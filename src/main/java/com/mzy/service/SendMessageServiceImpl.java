/*
package com.mzy.service;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

*/
/**
 * @Author Jack Miao
 * @date 2021/1/13 21:53
 * @desc
 *//*

@Service
public class SendMessageServiceImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendMessageServiceImpl.class);
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
    @Autowired
    private ConnectionFactory factory;

    public void sendMsgToAmqp(String message) {
        LOGGER.info("Amqp send msg is : {}", message);
        Connection connection = null;
        try {
            */
/*ConnectionFactory factory = new ConnectionFactory();
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
            factory.setShutdownTimeout(0);*//*

            connection = factory.newConnection();
            Channel channel = connection.createChannel();
            // 生成MessageId
            String reqId = UUID.randomUUID().toString();
            LOGGER.info("reqId is : {}", reqId);
            AMQP.BasicProperties props = new AMQP.BasicProperties.Builder().messageId(reqId).build();
            // 开始发送消息
            channel.basicPublish(exchangeName, routingKey, true, props,
                    (message).getBytes(StandardCharsets.UTF_8));
            LOGGER.info("Aliyun Amqp send is successed.");
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("IOException is : {}", e, e.getMessage());
        } catch (TimeoutException e) {
            LOGGER.error("TimeoutException is : {}", e, e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (IOException e) {
                    LOGGER.error("IOException is : {}", e, e.getMessage());
                }
            }
        }
    }



    */
/**
     * @desc 消费MQ消息
     *//*

    public void receivedMsgToAmqp() {
        //LOGGER.info("Amqp received msg is starting.");
        Connection connection = null;
        //Map<String, Object> resultMaps = new HashMap<>();
        try {
            */
/*ConnectionFactory factory = new ConnectionFactory();
            // 设置接入点，在RabbitMQ版控制台实例详情页面查看
            factory.setHost("amqp-cn-oew20292n005.mq-amqp.cn-shanghai-867405-a.aliyuncs.com");
            // ${instanceId}为实例ID，在RabbitMQ版控制台概览页面查看
            // ${AccessKey}阿里云身份验证，在阿里云控制台创建
            // ${SecretKey}阿里云身份验证，在阿里云控制台创建
            factory.setCredentialsProvider(new AliyunCredentialsProvider(accKey, secKey, instanceId));
            //一定要这个才能自动恢复。
            factory.setAutomaticRecoveryEnabled(true);
            factory.setNetworkRecoveryInterval(5000);
            // 设置Vhost名称，请确保已在RabbitMQ版控制台上创建完成
            factory.setVirtualHost("mzy_demo_one");
            // 默认端口，非加密端口5672，加密端口5671
            factory.setPort(5672);
            factory.setConnectionTimeout(300 * 1000);
            factory.setHandshakeTimeout(300 * 1000);
            factory.setShutdownTimeout(0);*//*

            connection = factory.newConnection();
            final Channel channel = connection.createChannel();

            // 创建${ExchangeName}，该Exchange必须在RabbitMQ版控制台上已存在，并且Exchange的类型与控制台上的类型一致
            AMQP.Exchange.DeclareOk exchangeDeclareOk = channel.exchangeDeclare("mzy_exchange_one", "direct",
                    true, false, false, null);
            // 创建${QueueName} ，该Queue必须在RabbitMQ版控制台上已存在
            AMQP.Queue.DeclareOk queueDeclareOk = channel.queueDeclare("mzy_queue_one",
                    true, false, false, new HashMap<String, Object>());
            // Queue与Exchange进行绑定，并确认绑定的BindingKeyTest
            AMQP.Queue.BindOk bindOk = channel.queueBind("mzy_queue_one", "mzy_exchange_one", "mzy");
            // 开始消费消息
            channel.basicConsume("mzy_queue_one", false, "amqpDemoCustomer", new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    //接收到的消息，进行业务逻辑处理
                    String msg = new String(body, StandardCharsets.UTF_8);
                    String uid = properties.getMessageId();
                    LOGGER.info("receivedMsgToAmqp --> Received msg is : {}", msg);
                    LOGGER.info("receivedMsgToAmqp --> msgId is : {}", uid);
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("IOException is : {}", e, e.getMessage());
        } catch (TimeoutException e) {
            e.printStackTrace();
            LOGGER.error("TimeoutException is : {}", e, e.getMessage());
        } finally {
//			if (connection != null) {
//				try {
//					connection.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//					LOGGER.error("IOException is : {}", e, e.getMessage());
//				}
//			}
        }
    }
}
*/
