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

        String html = "";
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
        }

        html += ""
                + "<div class='container'>"
                + "  <div class='col-md-12'>"
                + "    <h2>TODO list</h2>"
                + "  </div>"
                + "</div>";

        html += "<div class='container'>"
                + "<form method='GET' action='/secure/todo.html' class='form-horizontal '>"
                + "<div class='form-group'>"
                + "  <input type='hidden' name='id' value='" + idText + "' />"
                + "  <div class='col-md-11'>"
                + "    <input type='text' class='form-control' name='todo' placeholder='Add task' value='" + todoText + "'>"
                + "  </div>"
                + "<div class='col-md-1'>"
                + "  <button type='submit' class='btn btn-primary pull-right'>Save</button>"
                + "</div>"
                + "</div>"
                + "</form>"
                + "</div>";

        List<TodoItem> todoItems = Repository.getTodoItems(user);
        for (TodoItem todoItem : todoItems) {
            html += "<div class='container'>"
                    + "<div class='col-md-12'>"
                    + escapeHtml4(todoItem.getTodo())
                    + "<a href='?id=" + todoItem.getId() + "'> [edit]</a>"
                    + "<a href='?id=" + todoItem.getId() + "&delete=true'> [delete]</a>"
                    + "</div>"
                    + "</div>";
        }

        response.getWriter().println(Html.html(html));
    }

    private boolean isNullOrEmpty(String id) {
        return id == null || id.isEmpty();
    }

}
