package cn.enjoyedu.msgreliability;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期视频咨询芊芊老师  QQ：2130753077  VIP课程咨询 依娜老师QQ：2133576719
 * 类说明：消费者端-异步接收
 */
public class JmsMsgReliablityConsumerAsyn {
    /*默认连接用户名*/
    private static final String USERNAME
            = ActiveMQConnection.DEFAULT_USER;
    /* 默认连接密码*/
    private static final String PASSWORD
            = ActiveMQConnection.DEFAULT_PASSWORD;
    /* 默认连接地址*/
    private static final String BROKEURL
            = ActiveMQConnection.DEFAULT_BROKER_URL;

    public static void main(String[] args) {
        /* 连接工厂*/
        ConnectionFactory connectionFactory;
        /* 连接*/
        Connection connection = null;
        /* 会话*/
        Session session;
        /* 消息的目的地*/
        Destination destination;
        /* 消息的消费者*/
        MessageConsumer messageConsumer;

        /* 实例化连接工厂*/
        connectionFactory
                = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKEURL);


        try {
            /* 通过连接工厂获取连接*/
            connection = connectionFactory.createConnection();
            /* 启动连接*/
            connection.start();
            /* 创建session*/
            session = connection.createSession(true,
                    Session.CLIENT_ACKNOWLEDGE);
            /* 创建一个消息队列*/
            destination = session.createQueue("MsgReliability");

            /* 创建消息消费者*/
            messageConsumer = session.createConsumer(destination);
            /* 设置消费者监听器，监听消息*/
            messageConsumer.setMessageListener(new MessageListener() {
                public void onMessage(Message message) {
                    try {
                        //do my work
                        System.out.println(((TextMessage) message).getText());
                        message.acknowledge();
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                    //throw new RuntimeException("RuntimeException");
                }
            });
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
