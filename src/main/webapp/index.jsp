<%@ page import="java.util.ArrayList" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ru" >
<head>
    <meta charset="UTF-8">
    <title>Lab2</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <link href="styles.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="scripts.js"></script>
</head>

<body onload="loaded()">

<table class="header">
    <tr>
        <td class="nameAndGroup">
            Попов Андрей P32091
        </td>
    </tr>
    <tr>
        <td class="var">
            Вариант 1118
        </td>
    </tr>
</table>

<table>
    <tbody>
    <form name="mainPost" method="post" action="${pageContext.request.contextPath}/lab2">
        <tr>
            <td class="lefted">
                <object id="pict" class="svg" data="drawing_and.svg"></object>
                <object class="svg" data="graph.svg"></object>
                <svg xmlns="http://www.w3.org/2000/svg" style="position: relative" viewBox="0 0 120 120" id="graph">
                    <% ServletContext context = request.getServletContext();
                        ArrayList<Object[]> data = new ArrayList<>();
                        Object raw_data = context.getAttribute("data");
                        if (raw_data == null)
                            context.setAttribute("data", data);
                        else
                            data = (ArrayList<Object[]>) raw_data;
                        for (Object[] el: data) {%>
                    <circle cx="<%=(int)(60 + (double) el[0] * (10))%>" cy="<%=(int)(60 - (double) el[1] * 10)%>" r="2%" fill="red"></circle>
                    <%}
                    %>
                </svg>

            </td>
            <td class="lefted">
                <table class="input">
                    <tr>
                        <td>
                            <table>
                                <tr>
                                    <td>R :</td>
                                    <% for(double i = 1; i <= 5; i += 1) {%>
                                        <td><label><input type="checkbox" class="inpR" name="inpR" value="<%=i%>" oninput="validate(false, 0, 0)"> <%=i%></label></td>
                                   <%}%>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Y : <input placeholder="Введите Y, (-3 < Y < -3)" type="text" name="inpY" id="inpY" maxlength="6" oninput="validate(false, 0, 0)">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <select id="inpX" name="inpX" oninput="validate(false, 0, 0)">
                                <option id="hehe" value="0" selected>Выберите X:</option>>
                                <% for(double i = -4; i <= 4; i += 1) {%>
                                <option value="<%=i%>" selected><%=i%></option>
                                <%}%>
                            </select>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td class="righted">
                <input type="submit" value="Отправить и посмотреть результаты" id="submit" name="submit"/>
            </td>
            <td>
                <div id="err"></div>
            </td>
        </tr>
    </form>
    </tbody>
</table>
</body>
</html>