import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);

        String content = "<h3>You are now logged out!</h3>";
        
        response.getWriter().println(Html.html(content));
    }
}
