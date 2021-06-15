package service;

import entity.User;
import repository.UserRepository;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.enterprise.context.ApplicationScoped;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional
@WebService
public class UserService {
    @EJB
    private UserRepository r;

    @WebMethod
    public User findUserByEmail(String email) {
        try {
            return r.findUserByEmail(email);
        } catch (NoResultException | EJBTransactionRolledbackException e) {
            return null;
        }
    }
}
