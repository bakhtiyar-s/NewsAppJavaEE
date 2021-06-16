package jsmconsumer;

import entity.News;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "/jms/queue/NewsAppQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
}, mappedName = "/jms/queue/NewsAppQueue")
public class JmsConsumer implements MessageListener {

    @Override
    public void onMessage(Message message) {
        System.out.println("From JMS consumer: ");
        try {
            String title = message.getBody(News.class).getTitle();
            String date = message.getBody(News.class).getNewsDate().toString();
            System.out.println("News has been added with title '" + title + "' on " + date);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
