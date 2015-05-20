import java.security.Principal;

import javax.security.auth.Subject;
import javax.servlet.ServletRequest;

import org.eclipse.jetty.security.DefaultIdentityService;
import org.eclipse.jetty.security.DefaultUserIdentity;
import org.eclipse.jetty.security.IdentityService;
import org.eclipse.jetty.server.UserIdentity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginService implements org.eclipse.jetty.security.LoginService {

    private static final Logger log = LoggerFactory.getLogger(LoginService.class);

    private IdentityService identityService = new DefaultIdentityService();

    @Override
    public String getName() {
        return LoginService.class.getName();
    }

    @Override
    public UserIdentity login(final String username, Object credentials, ServletRequest request) {
        String password = (String) credentials;

        boolean login = Repository.login(username, password);
        if (!login) {
            log.info("Wrong username or password");
            return null;
        } else {
            Principal userPrincipal = new Principal() {
                @Override
                public String getName() {
                    return username;
                }
            };
            String[] roles = new String[] { "user" };
            Subject subject = new Subject();
            subject.getPrincipals().add(userPrincipal);
            subject.getPrivateCredentials().add(credentials);

            log.info(username + " logged in");
            return new DefaultUserIdentity(subject, userPrincipal, roles);
        }
    }

    @Override
    public boolean validate(UserIdentity user) {
        return true;
    }

    @Override
    public IdentityService getIdentityService() {
        return identityService;
    }

    @Override
    public void setIdentityService(IdentityService service) {
        this.identityService = service;
    }

    @Override
    public void logout(UserIdentity user) {
        log.info(user.getUserPrincipal().getName() + " logged out");
    }

}
