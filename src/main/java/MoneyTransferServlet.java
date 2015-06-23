import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MoneyTransferServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);

        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String amount = request.getParameter("amount");

        String user = request.getUserPrincipal().getName();

        String currentAmount = Repository.getAmount(user);

        String content = "<form method='GET' action='/secure/transfer.html' "
                + "class='form-horizontal col-md-offset-4 col-lg-offset-4 col-xs-offset-4 col-md-4 col-lg-4 col-xs-4'>"
                + "<div class='form-group'>"
                + "  <div class='col-lg-10'>"
                + "    Transfer"
                + "  </div>"
                + "  <div class='col-lg-10'>"
                + "    <input type='text' class='form-control input-block-level' placeholder='amount' name='amount'/><br>"
                + "  </div>"
                + "  <div class='col-lg-10'>"
                + "    to "
                + "  </div>"
                + "  <div class='col-lg-10'>"
                + "    <input type='text' class='form-control input-block-level' placeholder='username' name='to'/><br>"
                + "  </div>"
                + "</div>"
                + "<button type='submit' class='btn btn-primary pull-right'>Transfer money</button>"
                + "</form>"
                + "You have " + currentAmount + " dollars";

        if (from != null && !from.isEmpty() && to != null && !to.isEmpty() && amount != null && !amount.isEmpty()) {
            // TODO:
            // Repository.transferMoney(from, to, amount);
            content += "<p>" + amount + " transfered from " + from + " to " + to + "</p>";
        }

        response.getWriter().println(Html.html(content));
    }
}
