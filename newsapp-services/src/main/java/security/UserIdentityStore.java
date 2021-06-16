package security;

import entity.Role;
import entity.User;
import org.slf4j.Logger;
import service.UserService;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.security.auth.login.CredentialException;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import java.util.Set;
import java.util.stream.Collectors;

import static javax.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;

public class UserIdentityStore implements IdentityStore {

    @Inject
    private UserService userService;

    @Inject
    private Logger logger;

    @Override
    public CredentialValidationResult validate(Credential credential) {
        String caller = ((UsernamePasswordCredential)credential).getCaller();
        try {
            User user = userService.authenticate(caller, ((UsernamePasswordCredential)credential).getPasswordAsString());
            Set<String> roles = user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toSet());
            return new CredentialValidationResult(caller, roles);
        } catch (NoResultException e) {
            logger.error("User {} not found", caller);
        } catch (CredentialException e) {
            logger.error("Wrong password for user {}", caller);
        }
        return INVALID_RESULT;
    }
}
