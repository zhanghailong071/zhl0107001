package cn.enjoyedu.usemq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期视频咨询芊芊老师  QQ：2130753077  VIP课程咨询 依娜老师QQ：2133576719
 * 类说明：生产者端
 */
public class JmsMsProducer {

    /*默认连接用户名*/
    private static final String USERNAME
            = ActiveMQConnection.DEFAULT_USER;
    /* 默认连接密码*/
    private static final String PASSWORD
            = ActiveMQConnection.DEFAULT_PASSWORD;
    /* 默认连接地址*/
    private static final String BROKEURL
            = "failover:(tcp://localhost:61616,tcp://localhost:61617," +
            "tcp://localhost:61618)?randomize=false";
    private static final int SENDNUM = 3;

    public static void main(String[] args) {
        /* 连接工厂*/
        ConnectionFactory connectionFactory;
        /* 连接*/
        Connection connection = null;
        /* 会话*/
        Session session;
        /* 消息的目的地*/
        Destination destination;
        /* 消息的生产者*/
        MessageProducer messageProducer;

        /* 实例化连接工厂*/
        connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD,
                BROKEURL);
        try {
            /* 通过连接工厂获取连接*/
            connection = connectionFactory.createConnection();
            /* 启动连接*/
            connection.start();
            /* 创建session
             * 第一个参数表示是否使用事务，第二次参数表示是否自动确认*/
            session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            /* 创建一个名为HelloWorld消息队列*/
            destination = session.createQueue("jdbcms");
            /* 创建消息生产者*/
            messageProducer = session.createProducer(destination);
            /* 循环发送消息*/
            for (int i = 0; i < SENDNUM; i++) {
                String msg = "发送消息" + i + " " + System.currentTimeMillis();
                TextMessage textMessage = session.createTextMessage(msg);
                System.out.println("标准用法:" + msg);
                messageProducer.send(textMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }

        }
    }


}
