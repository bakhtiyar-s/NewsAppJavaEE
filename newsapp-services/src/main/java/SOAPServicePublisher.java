import jakarta.xml.ws.Endpoint;
import service.NewsService;

public class SOAPServicePublisher {
    public static void main(String[] args) {
        Endpoint.publish(
                "http://localhost:8080/newsapp-web/newsservice",
                new NewsService());
    }
}

