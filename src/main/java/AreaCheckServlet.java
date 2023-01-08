import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@WebServlet(value="AreaCheckServlet")
public class AreaCheckServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("");
        dispatcher.forward(req, resp);
    }


    static String PAGE_HEADER = "<!DOCTYPE html>" +
            "<html lang=\"ru\" >\n" +
            "<head>" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <link href=\"styles.css\" rel=\"stylesheet\" type=\"text/css\">\n" +
            "    <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js\"></script>\n" +
            "    <script type=\"text/javascript\" src=\"scripts.js\"></script>\n" +
            "</head>\n" +
            "<body>\n" +
            "<table class=\"mainTable\">\n" +
            "    <tr>\n" +
            "        <td>\n" +
            "                <button class=\"button\" onclick=\"history.back();\">Вернуться назад</button>\n" +
            "            </td>"+
            "    </tr>";


    static String PAGE_FOOTER = "</table></body></html>";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("in progress");
        ServletContext context = getServletContext();
        ArrayList<Object[]> data = new ArrayList<>();
        Object raw_data = context.getAttribute("data");
        if (raw_data == null)
            context.setAttribute("data", data);
        else
            data = (ArrayList<Object[]>) raw_data;

        resp.setContentType("text/html");

        resp.setCharacterEncoding("UTF-8");

        PrintWriter writer = resp.getWriter();

        writer.println(PAGE_HEADER);

        Double x = 0d, y = 0d;

        ArrayList<Double> r = new ArrayList<>();

        boolean xIsLegal = true, yIsLegal = false;
        ArrayList<Boolean> rIsLegal = new ArrayList<>();

        try{
            x = Double.parseDouble(req.getParameter("inpX"));
            y = Double.parseDouble(req.getParameter("inpY"));
            String rStrings[] = req.getParameterValues("inpR");

             for (String rString : rStrings) {
                r.add(Double.parseDouble(rString));
             }

             yIsLegal = (-3 < y && y < 3);

            for (Double i:
                 r) {
                rIsLegal.add((i == 1 || i == 2 || i == 3 || i == 4 || i == 5));
            }
        }
        catch (Exception e){
        }
        if(xIsLegal && yIsLegal)
        for (int i = 0; i < r.size(); i++){
            if(rIsLegal.get(i)){
                Double R = r.get(i);
                if (!data.contains(new Object[]{x, y, R, false}) && !data.contains(new Object[]{x, y, R, true}) ) {
                    if ((x >= 0 && y >= 0 && y <= R - x)
                            || (x <= 0 && y <= 0 && y >= -R / 2 && x >= -R)
                            || (x <= 0 && y >= 0 && x * x + y * y <= R * R)) {
                        data.add(new Object[]{x, y, r.get(i), true});
                        continue;
                    }
                    data.add(new Object[]{x, y, r.get(i), false});
                }
            }
        }

        String GRAPH = "<tr>" +
                "<table>" +
                "<tr>" +
                "<td>" +
                "<img class=\"img\" src=\"drawing_and.svg\" width=\"50%\">\n" +
                "                <img class=\"img\" src=\"graph.svg\" width=\"50%\">\n" +
                "                <svg style=\"position: relative\" xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 120 120\" id=\"graph\">\n";
                for (Object[] el: data)
                    GRAPH = GRAPH + "<circle cx=\"" + (int)(60 + (double)el[0]*10) + "\" cy=\"" + (int)(60 - (double)el[1]*10) + "\" r=\"2%\" fill=\"red\"></circle>\n";
                GRAPH = GRAPH + "                </svg>" +
                "</td>";

        String TABLE = "<td><table class=\"outputTable\">" +
                "<tr class=\"tableHead\"><td>X</td><td>Y</td><td>R</td><td>RESULT</td></tr>";

        for (Object[] i:
             data) {
            if((boolean)i[3])
                TABLE = TABLE + "<tr class=\"highlightable\"><td>" +
                    i[0] + "</td><td>" +
                    i[1] + "</td><td>" +
                    i[2] + "</td>" +
                     "<td class=\"true\">true";
            else
                TABLE = TABLE + "<tr class=\"highlightable\"><td>" +
                        i[0] + "</td><td>" +
                        i[1] + "</td><td>" +
                        i[2] + "</td>" +
                        "<td class=\"false\">false";
            TABLE = TABLE + "</td></tr>";
        }
        TABLE = TABLE + "</table>\n" +
                "</td>" +
                "</tr>" +
                "</table>" +
                "        </td>\n" +
                "    </tr>";

        writer.println(GRAPH + TABLE);

        writer.println(PAGE_FOOTER);
    }
}
