<%@ page import="il.ac.hit.todolist.model.User" %><%--
  Created by IntelliJ IDEA.
  User: Lihay
  Date: 11/05/2019
  Time: 19:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="il.ac.hit.todolist.model.*" %>

<html>
<head>

    <title>To Do List Manager</title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" href="Styles/styles.css"/>
    <link rel="stylesheet" href="//code.jquery.com/mobile/1.3.1/jquery.mobile-1.3.1.min.css"/>
    <script src="//code.jquery.com/jquery-1.9.1.min.js"></script>
    <script src="//code.jquery.com/mobile/1.3.1/jquery.mobile-1.3.1.min.js"></script>

</head>
<body>

<div data-role="page" data-theme="b" id="home">

    <div data-role="header">
        <h1> To Do List Manager </h1>

        <%
            User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
            if (loggedInUser != null) {
        %>
        <p> Hi <%=loggedInUser.getUsername()%>
        </p>
        <% } else { %>
        <a data-rel="dialog"
           href="<%=request.getServletContext().getContextPath()%>/router/navigator/location?goto=login"
           data-theme="c"> Login </a>
        <a data-rel="dialog"
           href="<%=request.getServletContext().getContextPath()%>/router/navigator/location?goto=register"
           data-theme="c"> Register </a>
        <% } %>

    </div>

    <div data-role="content">
        <a data-role="button" data-inline="true"
           href="<%=request.getServletContext().getContextPath()%>/router/task/getTasksList"
           data-theme="b">My List</a>
    </div>

    <button onclick="alert('JavaScript is working!')">JavaScript Test</button>

</div>

</body>
</html>
