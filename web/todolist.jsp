<%@ page import="il.ac.hit.todolist.model.Task" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Lihay
  Date: 03/06/2019
  Time: 19:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <title>My To Do List</title>

    <link rel="stylesheet" href="http://code.jquery.com/mobile/1.3.1/jquery.mobile-1.3.1.min.css"/>
    <script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
    <script src="http://code.jquery.com/mobile/1.3.1/jquery.mobile-1.3.1.min.js"></script>

    <style>
        body {
            text-align: center;
        }
    </style>

</head>
<body>

<div data-role="page" data-theme="b" id="todolist">

    <%
        List<Task> tasks = (List<Task>) request.getAttribute("tasks");
        System.out.println("tasks: " + tasks);
    %>

    <div data-role="header">
        <h1>My To Do List</h1>
        <a href="<%=request.getServletContext().getContextPath()%>" data-theme="c" data-role="button" data-icon="home">Home</a>
    </div>

    <div data-role="content">
        <a href="<%=request.getServletContext().getContextPath()%>/addtask.jsp" data-role="button" data-rel="dialog"
           data-inline="true" data-icon="add">Add item</a>
        <br> <br>
        <% if (tasks != null) { %>
        <ul data-role="listview" data-filter="true" data-theme="c">
            <% for (Task task : tasks) { %>
            <li>
                <a href="<%=request.getServletContext().getContextPath()%>/updatetask.jsp?taskID=<%=task.getTaskID()%>&status=<%=task.getStatus()%>"
                   data-rel="dialog" data-inline="true"><%=task.getDescription()%>
                    <span style="float: right">(<%=task.getDeadline()%>)</span>
                </a>
                <a data-inline="true" data-icon="delete">delete</a>
            </li>
            <% } %>
        </ul>

        <% } else { %>
        <p>You list is empty.</p>
        <% } %>
    </div>

</div>

</body>
</html>
