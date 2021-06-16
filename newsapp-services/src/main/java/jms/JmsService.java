package jms;

import entity.News;
import org.slf4j.Logger;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.*;

@ApplicationScoped
public class JmsService {

    @Resource(mappedName = "java:/jms/queue/NewsAppQueue")
    private Queue newsAppQueue;

    @Inject
    @JMSConnectionFactory("java:/ConnectionFactory")
    private JMSContext context;

    @Inject
    private Logger logger;

    public void sendMessage(News news) {
        try {
            ObjectMessage objectMessage = context.createObjectMessage(news);
            context.createProducer().send(newsAppQueue, objectMessage);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

    }
}
