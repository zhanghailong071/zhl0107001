
package cn.enjoyedu.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期视频咨询芊芊老师  QQ：2130753077  VIP课程咨询 依娜老师QQ：2133576719
 * 类说明：
 */
@Component
public class QueueReceiver1 implements MessageListener {

    @Autowired
    private ReplyTo replyTo;

    public void onMessage(Message message) {
        try {
            String textMsg = ((TextMessage) message).getText();
            System.out.println("QueueReceiver1 accept msg : " + textMsg);
            // do business work;
            replyTo.send(textMsg,message);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
