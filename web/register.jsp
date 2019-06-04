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

    <link rel="stylesheet" href="http://code.jquery.com/mobile/1.3.1/jquery.mobile-1.3.1.min.css"/>
    <script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
    <script src="http://code.jquery.com/mobile/1.3.1/jquery.mobile-1.3.1.min.js"></script>

</head>
<body>

<div data-role="page" data-theme="b" id="register">

    <div data-role="header">
        <h1>Registration</h1>
    </div>

    <div data-role="content">

        <div date-role="form">
            <p>Please register to edit your to do list.</p>
            <input type="text" name="username" placeholder="Enter your username" id="registerUsername">
            <input type="password" name="password" placeholder="Enter your password" id="registerPassword">
            <input data-role="button" type="submit" value="Submit">
        </div>

    </div>

    <div data-role="footer">
        <a data-rel="dialog" data-role="button" data-inline="true"
           href="<%=request.getServletContext().getContextPath()%>/login.jsp">Login</a>
    </div>

</div>

</body>
</html>
