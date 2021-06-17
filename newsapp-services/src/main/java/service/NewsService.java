package service;


import entity.News;
import jms.JmsService;
import repository.NewsRepository;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.NotFoundException;
import java.util.List;

@ApplicationScoped
@WebService
public class NewsService {

    @EJB
    private NewsRepository r;

    @Inject
    private JmsService jmsService;

    @WebMethod
    public List<News> findAll() throws NotFoundException {
        List<News> news = r.findAll();
        if (!news.isEmpty()) {
            return news;
        } else {
            throw new NotFoundException("No news were found");
        }
    }

    @WebMethod
    public News save(News entity) throws Exception {
        try {
            News savedNews = r.save(entity);
            jmsService.sendMessage(savedNews);
            return savedNews;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @WebMethod
    public News update(News entity, Integer id) throws NotFoundException, Exception {
        News oldNews = this.findById(id);
        entity.setId(oldNews.getId());
        try {
            return r.update(entity);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    @WebMethod
    public News findById(Integer id) throws NotFoundException {
        News news = r.findById(id);
        if (news != null) {
            return news;
        } else {
            throw new NotFoundException("News with id " + id + " was not found");
        }
    }

    @WebMethod
    public void deleteById(Integer id) throws NotFoundException {
        News news = findById(id);
        if (news != null) {
            r.delete(news);
        } else {
            throw new NotFoundException("News with id " + id + " was not found");
        }

    }
}
