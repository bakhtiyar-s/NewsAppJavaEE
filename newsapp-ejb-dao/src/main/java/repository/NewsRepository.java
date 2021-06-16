package repository;


import entity.News;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.transaction.Transactional;

@Stateless
@Local
@Transactional
public class NewsRepository extends AbstractRepository<News, Integer> {

    public NewsRepository() {
        super(News.class);
    }
}
