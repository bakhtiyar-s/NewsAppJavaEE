package service;

import entity.User;
import repository.UserRepository;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.enterprise.context.ApplicationScoped;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.security.auth.login.CredentialException;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.logging.Level;

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

    public User authenticate(@NotNull final String email, @NotNull final String password) throws CredentialException, NoResultException {
        User user = r.findUserByEmail(email);
        if (user == null) {
            throw new NoResultException("User was not found");
        }
        if (!user.getPassword().equals(password)) {
            throw new CredentialException("Provided password does not match");
        }
        return user;

    }
}
