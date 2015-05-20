import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);

        String searchString = request.getParameter("q");

        String content = "<form method='GET' action='/search.html' "
                + "class='form-horizontal col-md-offset-4 col-lg-offset-4 col-xs-offset-4 col-md-4 col-lg-4 col-xs-4'>"
                + "<div class='form-group'>"
                + "  <div class='col-lg-10'>"
                + "    <input type='text' class='form-control input-block-level' placeholder='Enter search string' name='q'/><br>"
                + "  </div>"
                + "</div>"
                + "<button type='submit' class='btn btn-primary pull-right'>Search</button>"
                + "</form>";

        if (searchString != null && !searchString.isEmpty()) {
            content += "<div class='col-lg-10'>"
                    + "Found no documents matching «" + searchString + "»."
                    + "</div>";
        }

        response.getWriter().println(Html.html(content));

    }

}
