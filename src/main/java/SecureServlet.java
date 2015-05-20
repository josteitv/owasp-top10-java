import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SecureServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);

        String content = "<h1>You are logged in to the secure area as '" + request.getUserPrincipal().getName() + "'.</h1>"
                + "<h3><a href='/secure/todo.html'>View TODO list</a></h3>"
                + "<h3><a href='/logout'>Log out of secure area</a></h3>";

        response.getWriter().println(Html.html(content));
    }
}
