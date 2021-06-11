package repository;

import entity.User;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Stateless
@Local
public class UserRepository extends AbstractRepository<User, Integer>{

    public UserRepository() {
        super(User.class);
    }

    public User findUserByEmail(String email) {
        CriteriaBuilder criteriaBuilder = super.entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("email"), email));
        return super.entityManager.createQuery(criteriaQuery).getSingleResult();
    }
}
