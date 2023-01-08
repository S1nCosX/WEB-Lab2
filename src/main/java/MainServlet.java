import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet("/lab2")
public class MainServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher;
        Map<String, String[]> params = req.getParameterMap();
        if (params.containsKey("inpX") && params.containsKey("inpY") && params.containsKey("inpR"))
            dispatcher = req.getRequestDispatcher("AreaCheckServlet");
        else
        {
            if (params.containsKey("clear"))
                getServletContext().removeAttribute("data");
            dispatcher = req.getRequestDispatcher("index.jsp");
        }
        dispatcher.forward(req, resp);
    }
}
