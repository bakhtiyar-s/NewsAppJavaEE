package repository;


import entity.News;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local
public class NewsRepository extends AbstractRepository<News, Integer> {

    public NewsRepository() {
        super(News.class);
    }
}
