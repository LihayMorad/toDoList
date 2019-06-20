<%--
  Created by IntelliJ IDEA.
  User: Alex
  Date: 18/05/2019
  Time: 16:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <title>Register</title>

    <link rel="stylesheet" href="//code.jquery.com/mobile/1.3.1/jquery.mobile-1.3.1.min.css"/>
    <script src="//code.jquery.com/jquery-1.9.1.min.js"></script>
    <script src="//code.jquery.com/mobile/1.3.1/jquery.mobile-1.3.1.min.js"></script>

</head>
<body>

<div data-role="page" data-theme="b" id="register">

    <div data-role="header">
        <h1>Registration</h1>
    </div>

    <div data-role="content">
        <% String errorMessage = (String) request.getAttribute("errorMessage");
            if (errorMessage != null) {
        %>
        <p class="errorMessage"><%=errorMessage%></p>
        <% } %>

        <form method="post" action="${pageContext.request.contextPath}/router/user/register">
            <p>Registration</p>
            <input type="text" name="username" placeholder="Enter your username" id="registerUsername">
            <input type="password" name="password" placeholder="Enter your password" id="registerPassword">
            <input data-role="button" type="submit" value="Submit">

            <!-- <a data-role="button" data-inline="true">Forgot password?</a> -->
        </form>

    </div>

    <div data-role="footer">
        <a data-rel="dialog" data-role="button" data-inline="true"
           href="<%=request.getServletContext().getContextPath()%>/login.jsp">Login</a>
    </div>

</div>

</body>
</html>
