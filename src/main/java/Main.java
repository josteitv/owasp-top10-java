import java.net.URISyntaxException;

import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.authentication.FormAuthenticator;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.security.Constraint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final int PORT = 8080;
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        Server server = new Server(PORT);

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] {
                createServletContextHandler()
        });
        server.setHandler(handlers);

        Repository.createDatabase();

        server.start();
        log.info("OWASP Top 10 started at " + server.getURI());

        server.join();
    }

    private static ServletContextHandler createServletContextHandler() {
        ServletContextHandler servletHandler = new ServletContextHandler(ServletContextHandler.SESSIONS
                | ServletContextHandler.SECURITY);
        servletHandler.setContextPath("/");
        servletHandler.setWelcomeFiles(new String[] { "index.html" });
        servletHandler.setResourceBase(System.getProperty("java.io.tmpdir"));

        servletHandler.addServlet(SecureServlet.class, "/secure/*");
        servletHandler.addServlet(MoneyTransferServlet.class, "/secure/transfer.html");
        servletHandler.addServlet(TodoServlet.class, "/secure/todo.html");
        servletHandler.addServlet(LoginServlet.class, "/login");
        servletHandler.addServlet(LogoutServlet.class, "/logout");
        servletHandler.addServlet(SearchServlet.class, "/search.html");
        servletHandler.addServlet(FileServlet.class, "/file.html");

        ServletHolder staticServlet = new ServletHolder(DefaultServlet.class);
        staticServlet.setInitParameter("resourceBase", getResourceBase());
        staticServlet.setInitParameter("dirAllowed", "true");
        servletHandler.addServlet(staticServlet, "/");

        Constraint constraint = new Constraint();
        constraint.setName(Constraint.__FORM_AUTH);
        constraint.setRoles(new String[] { "user" });
        constraint.setAuthenticate(true);

        ConstraintMapping constraintMapping = new ConstraintMapping();
        constraintMapping.setConstraint(constraint);
        constraintMapping.setPathSpec("/secure/*");

        ConstraintSecurityHandler securityHandler = new ConstraintSecurityHandler();
        securityHandler.addConstraintMapping(constraintMapping);
        securityHandler.setLoginService(new LoginService());

        FormAuthenticator authenticator = new FormAuthenticator("/login", "/login?failed", false);
        securityHandler.setAuthenticator(authenticator);

        servletHandler.setSecurityHandler(securityHandler);

        return servletHandler;
    }

    private static String getResourceBase() {
        try {
            return Main.class.getResource("/web").toURI().toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
