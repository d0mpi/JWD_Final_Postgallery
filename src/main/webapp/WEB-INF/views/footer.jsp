<%--
  Created by IntelliJ IDEA.
  User: mdoku
  Date: 22.05.2021
  Time: 14:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${cookie['language'].value}" scope="session"/>
<fmt:setBundle basename="text"/>
<html>
<head>
    <title>Title</title>
</head>
<body>
<footer>
    <ul class="footer-ul font-footer">
        <li class="footer-li-1">
            <img class="img-logo" src="../../images/logo-white.png" alt="logo">
        </li>
        <li class="footer-li-2"><fmt:message key="footerMyName"/></li>
        <li class="footer-li-3">---</li>
        <li class="footer-li-4">mdokuchaevp@gmail.com</li>
        <li class="footer-li-5">06.06.2021</li>
    </ul>
</footer>
</body>
</html>
