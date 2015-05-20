import static org.apache.commons.lang3.StringEscapeUtils.escapeHtml4;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TodoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_OK);

        String user = request.getUserPrincipal().getName();

        String id = request.getParameter("id");
        String todo = request.getParameter("todo");
        String delete = request.getParameter("delete");

        // UTF8-problem i chrome...
        // String html = "<h2>TODO for bruker '" + user + "'</h2>";
        String html = "<h2>TODO</h2>";

        String todoText = "";
        String idText = "";

        if (!isNullOrEmpty(id) && !isNullOrEmpty(delete)) {
            // Delete todo item
            Repository.deleteTodoItem(id, user);

        } else if (!isNullOrEmpty(id) && isNullOrEmpty(todo)) {
            // View todo item
            TodoItem todoItem = Repository.getTodoItem(id);
            todoText = escapeHtml4(todoItem.getTodo());
            idText = id;

        } else if (isNullOrEmpty(id) && !isNullOrEmpty(todo)) {
            // Create new todo item
            Repository.saveTodoItem(new TodoItem(user, todo));

        } else if (!isNullOrEmpty(id) && !isNullOrEmpty(todo)) {
            // Update todo item
            Repository.updateTodoItem(new TodoItem(id, user, todo));
            todoText = escapeHtml4(todo);
            idText = id;
        }

        html += "<div class='container'>"
                + "<form method='GET' action='/secure/todo.html' "
                + "class='form-horizontal '>"
                + "<div class='form-group'>"
                + "  <input type='hidden' name='id' value='" + idText + "' />"
                + "  <div class='col-lg-10'>"
                + "    <textarea class='form-control' rows='5' name='todo'>"
                + todoText
                + "    </textarea>"
                + "  </div>"
                + "</div>"
                + "<button type='submit' class='btn btn-primary pull-right'>Save</button>"
                + "</form>"
                + "</div>";

        html += "<div class='container'>"
                + "<div class='col-lg-10'>"
                + "<a href='/secure/todo.html'>"
                + "&lt;create new item&gt;"
                + "</a>"
                + "</div>"
                + "</div>";

        List<TodoItem> todoItems = Repository.getTodoItems(user);
        for (TodoItem todoItem : todoItems) {
            html += "<div class='col-lg-10'>"
                    + "<a href='?id=" + todoItem.getId() + "'>"
                    + escapeHtml4(todoItem.getTodo())
                    + "</a>"
                    + "<a href='?id=" + todoItem.getId() + "&delete=true'> [x]</a>"
                    + "</div>";
        }

        response.getWriter().println(Html.html(html));
    }

    private boolean isNullOrEmpty(String id) {
        return id == null || id.isEmpty();
    }

}
