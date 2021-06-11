package service;

import entity.User;
import repository.UserRepository;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional
public class UserService {
    @EJB
    private UserRepository r;

    public User findUserByEmail(String email) {
        try {
            return r.findUserByEmail(email);
        } catch (NoResultException | EJBTransactionRolledbackException e) {
            return null;
        }
    }
}
