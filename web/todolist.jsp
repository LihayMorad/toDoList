<%@ page import="il.ac.hit.todolist.model.Task" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDateTime" %><%--
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

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" href="Styles/styles.css"/>
    <link rel="stylesheet" href="//code.jquery.com/mobile/1.3.1/jquery.mobile-1.3.1.min.css"/>
    <script src="//code.jquery.com/jquery-1.9.1.min.js"></script>
    <script src="//code.jquery.com/mobile/1.3.1/jquery.mobile-1.3.1.min.js"></script>

</head>
<body>

<div data-role="page" data-theme="b" id="todolist">

    <div data-role="header">
        <h1>My To Do List</h1>
        <a href="<%=request.getServletContext().getContextPath()%>" data-theme="c" data-role="button" data-icon="home">Home</a>
    </div>

    <div data-role="content">
        <a href="<%=request.getServletContext().getContextPath()%>/router/navigator/location?goto=addtask"
           data-role="button" data-rel="dialog"
           data-inline="true" data-icon="add" class="btn btn-secondary">Add item</a>
        <br> <br>

        <%
            String errorMessage = (String) request.getAttribute("errorMessage");
            if (errorMessage != null) {
        %>
        <p class="alert alert-danger text-danger"><%=errorMessage%>
        </p>
        <% } %>

        <%
            List<Task> tasks = (List<Task>) request.getSession().getAttribute("tasks");
            if (tasks.size() != 0) {
        %>
        <ol data-role="listview" data-filter="true">
            <%
                for (Task task : tasks) {
                    LocalDateTime deadline = LocalDateTime.parse(task.getDeadline());
            %>
            <li <% String theme = task.getStatus() ? "c" : "b"; %> data-theme="<%=theme%>">
                <a href="<%=request.getServletContext().getContextPath()%>/router/navigator/location?goto=updatetask&taskID=<%=task.getTaskID()%>&listID=<%=task.getListID()%>&status=<%=task.getStatus()%>"
                   data-rel="dialog" data-inline="true">
                    <%=task.getDescription()%>
                    <p class="deadline float-right">(<%=deadline.toLocalDate() + ", " + deadline.toLocalTime()%>)</p>
                </a>
                <a href="<%=request.getServletContext().getContextPath()%>/router/task/deleteTask?taskID=<%=task.getTaskID()%>&listID=<%=task.getListID()%>"
                   data-inline="true" data-icon="delete">delete</a>
            </li>
            <% } %>
        </ol>

        <% } else { %>
        <p class="alert alert-info">Your list is empty.</p>
        <% } %>
    </div>

</div>

</body>
</html>
