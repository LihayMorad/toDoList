<%--
  Created by IntelliJ IDEA.
  User: Lihay
  Date: 03/06/2019
  Time: 19:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <title>Title</title>

    <link rel="stylesheet" href="//code.jquery.com/mobile/1.3.1/jquery.mobile-1.3.1.min.css"/>
    <script src="//code.jquery.com/jquery-1.9.1.min.js"></script>
    <script src="//code.jquery.com/mobile/1.3.1/jquery.mobile-1.3.1.min.js"></script>

</head>
<body>

<div data-role="page" data-theme="b" id="updatetask">

    <div data-role="header">
        <h1>Edit your task</h1>
    </div>

    <div data-role="content">

        <%
            int taskID = Integer.valueOf(request.getParameter("taskID"));
            int listID = Integer.valueOf(request.getParameter("listID"));
        %>

        <form action="<%=request.getServletContext().getContextPath()%>/router/task/updateTaskStatus" method="post">
            <%--            <input type="text" name="status" value="<%=request.getParameter("status")%>">--%>
            <div data-role="fieldcontain">
                <label for="flipper">Task done:</label>
                <select name="status" id="flipper" data-role="slider">
                    <option value="true">Yes</option>
                    <option value="false">No</option>
                </select>
            </div>
            <input type="hidden" name="taskID" value="<%=taskID%>">
            <input type="hidden" name="listID" value="<%=listID%>">
            <input type="submit" value="Save">
        </form>

    </div>

</div>

</body>
</html>
