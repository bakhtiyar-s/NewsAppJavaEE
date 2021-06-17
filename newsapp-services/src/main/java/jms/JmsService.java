package jms;

import entity.News;
import org.slf4j.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.*;
import javax.naming.InitialContext;

@ApplicationScoped
public class JmsService {

    @Inject
    private Logger logger;

    public void sendMessage(News news) {
        try {
            InitialContext context = new InitialContext();
            QueueConnectionFactory factory =(QueueConnectionFactory)context.lookup("ConnectionFactory");
            QueueConnection connection = factory.createQueueConnection();
            connection.start();
            QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = (Queue) context.lookup("java:/jms/queue/NewsAppQueue");

            ObjectMessage objectMessage = session.createObjectMessage(news);
            objectMessage.setJMSExpiration(60 * 60 * 1000);
            objectMessage.setJMSPriority(9);

            session.createSender(queue).send(objectMessage);

            connection.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
