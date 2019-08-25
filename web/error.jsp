<%--
  Created by IntelliJ IDEA.
  User: Lihay
  Date: 03/06/2019
  Time: 20:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <title>Error</title>

    <link rel="stylesheet" href="Styles/styles.css"/>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" href="//code.jquery.com/mobile/1.3.1/jquery.mobile-1.3.1.min.css"/>
    <script src="//code.jquery.com/jquery-1.9.1.min.js"></script>
    <script src="//code.jquery.com/mobile/1.3.1/jquery.mobile-1.3.1.min.js"></script>

</head>
<body>
<h2 class="alert alert-danger text-danger">Error: <%=request.getAttribute("error")%></h2>
<a href="<%=request.getServletContext().getContextPath()%>" data-theme="c" data-role="button" data-icon="home"
   data-inline="true">Back to home page</a>

</body>
</html>
