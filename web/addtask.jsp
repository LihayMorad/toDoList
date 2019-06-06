<%--
  Created by IntelliJ IDEA.
  User: Lihay
  Date: 03/06/2019
  Time: 19:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <title>Add Task</title>

    <link rel="stylesheet" href="http://code.jquery.com/mobile/1.3.1/jquery.mobile-1.3.1.min.css"/>
    <script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
    <script src="http://code.jquery.com/mobile/1.3.1/jquery.mobile-1.3.1.min.js"></script>

</head>
<body>

<div data-role="page" data-theme="b" id="additem">

    <div data-role="header">
        <h1>Add your task</h1>
    </div>

    <div data-role="content">

        <form method="post" action="<%=request.getServletContext().getContextPath()%>/router/task/addTask">
            <label for="description">Enter a description for your new task:</label>
            <input type="text" name="description" placeholder="Enter description" id="description" required>
            <label for="deadline">Enter a deadline for your new task:</label>
            <input type="datetime-local" name="deadline" id="deadline" required>
            <input type="submit" value="Save">
        </form>

    </div>

</div>

</body>
</html>
