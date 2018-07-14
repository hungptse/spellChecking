<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="javax.net.ssl.HttpsURLConnection"%>
<%@page import="java.net.URL"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Spell Checking</title>
        <link rel="stylesheet" href="css/bootstrap.css">
    </head>

    <body style="overflow:hidden;">
        <div class="jumbotron" style="height: 59em;">
            <div style="height: 20em;" class="text-center">
                <h1>Spell Checking</h1>
                <a href="" class="text-success "> &copy; Copyright HungPT - CSD201</a>
            </div>

            <form action="SpellCheckingServlet" method="GET">
                <div class="input-group mb-3 col-8" style="margin-left: 15%">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Word need check</span>
                    </div>
                    <input type="text" class="form-control" name="txtSearch" autocomplete="off" value="${requestScope.SEARCH}">
                    <input type="submit" class="input-group-text mr-3" />
                </div>
            </form>



            <c:if test="${not empty requestScope.ERROR}">
                <div class="alert alert-danger col-4" style="margin-left: 24%">
                    <img src="img/exclamation-mark.svg" width="30px" height="30px"> &nbsp; ${requestScope.ERROR}
                </div>
            </c:if>
            <c:if test="${not empty requestScope.CORRECT}">
                <div class="alert alert-success col-4" style="margin-left: 24%">
                    <img src="img/checked.svg" width="30px" height="30px"> &nbsp; This word correctly: ${requestScope.CORRECT}
                </div>
            </c:if>
            <c:if test="${not empty requestScope.DATA}">
                <div class="alert alert-warning col-4" style="margin-left: 24%">
                    <img src="img/warning.svg" width="30px" height="30px"> &nbsp; This word can be
                </div>
                <div style="overflow-y: hidden; height: 400px; margin-left: 24%" class="col-6">
                    <ul class="list-group" style="margin-left: 10%">
                        <c:forEach items="${requestScope.DATA}" var="word">
                            <li class="list-group-item" style="margin-left: 5%">${word}</li>
                            </c:forEach>
                    </ul>
                </div>
            </c:if>
        </div>
    </body>

</html>