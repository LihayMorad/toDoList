<%--
  Created by IntelliJ IDEA.
  User: Lihay
  Date: 03/06/2019
  Time: 19:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <title>Login</title>

    <link rel="stylesheet" href="http://code.jquery.com/mobile/1.3.1/jquery.mobile-1.3.1.min.css"/>
    <script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
    <script src="http://code.jquery.com/mobile/1.3.1/jquery.mobile-1.3.1.min.js"></script>

</head>
<body>

<div data-role="page" data-theme="b" id="login">

    <div data-role="header">
        <h1>Login</h1>
    </div>

    <div data-role="content">

        <div date-role="form">
            <p>Please login to edit your to do list.</p>
            <input type="text" name="username" placeholder="Enter your username" id="loginUsername">
            <input type="password" name="password" placeholder="Enter your password" id="loginPassword">
            <input data-role="button" type="submit" value="Submit">

            <!-- <a data-role="button" data-inline="true">Forgot password?</a> -->
            <input type="submit" data-inline="true" value="Forgot password?">

        </div>

    </div>

    <div data-role="footer">
        <a data-rel="dialog" data-role="button" data-inline="true"
           href="<%=request.getServletContext().getContextPath()%>/register.jsp">Register</a>
    </div>

</div>

</body>
</html>
