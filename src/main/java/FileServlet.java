import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileServlet extends HttpServlet {
    
    // TODO: Hente ut filer

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_OK);

        String file = request.getParameter("file");

        if (file == null | file.isEmpty()) {
            response.setContentType("text/html");
            response.getWriter().println("<h1>No such file</h1>");
            return;
        }

        File fileName = new File(file);
        if (!fileName.exists() || !fileName.canRead()) {
            response.setContentType("text/html");
            response.getWriter().println("<h1>No such file</h1>");
            return;
        }

        byte[] bytes = Files.readAllBytes(Paths.get(file));

        // response.setContentType("text/html");
        response.getOutputStream().write(bytes);
    }

}
