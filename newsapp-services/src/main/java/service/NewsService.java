package service;


import entity.News;

import repository.NewsRepository;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.List;

@ApplicationScoped
@Transactional
@WebService
public class NewsService {

    @EJB
    private NewsRepository r;

    @WebMethod
    public List<News> findAll() { return r.findAll(); }

    @WebMethod
    public News save(News entity) {
        return  r.save(entity);
    }

    @WebMethod
    public News update(News entity) {
        return r.update(entity);
    }

    @WebMethod
    public News findById(Integer id) {
        return r.findById(id);
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
