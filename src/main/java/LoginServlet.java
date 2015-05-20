import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);

        String content = "<form method='POST' action='/j_security_check' "
                + "class='form-horizontal col-md-offset-4 col-lg-offset-4 col-xs-offset-4 col-md-4 col-lg-4 col-xs-4'>"
                + "<div class='form-group'>"
                + "  <div class='col-lg-10'>"
                + "    <input type='text' class='form-control input-block-level' id='usr' placeholder='Username' name='j_username'/>"
                + "  </div>"
                + "</div>"
                + "<div class='form-group'>"
                + "  <div class='col-lg-10'>"
                + "    <input type='text' class='form-control input-block-level' id='pwd' placeholder='Password' name='j_password'/>"
                + "  </div>"
                + "</div>"
                + "<button type='submit' class='btn btn-primary pull-right'>Log in</button>"
                + "</form>";

        if (request.getParameterMap().keySet().contains("failed")) {
            content += "<div class='col-lg-10'>"
                    + "Wrong username or password!"
                    + "</div>";
        }

        response.getWriter().println(Html.html(content));
    }

}
