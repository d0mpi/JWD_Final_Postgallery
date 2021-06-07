<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${cookie['language'].value}" scope="session"/>
<fmt:setBundle basename="text"/>
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <title>Register</title>
    <link rel="stylesheet" type="text/css" href="../../css/register-styles.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="../../fonts/fonts.css">
    <script src="../../js/jquery-3.6.0.min.js"></script>
    <script src="../../js/register.js"></script>

    <jsp:include page="title-logo.jsp"/>


</head>
<body>
<div class="sign-in-box">
    <a class="logo-row" href="${pageContext.request.contextPath}/home">
        <img src="../../images/logo-white.png" alt="logo">
    </a>
    <div class="text-top-row">
        <h3> <fmt:message key="registerTitle"/></h3>
    </div>
    <form class="form-row" method="post" name="reg_form"><%--        <div class="form-group">--%>
<%--            <div class="form-under">Email</div>--%>
<%--            <label for="email"></label>--%>
<%--            <input class="form-input" type="email" name="email" id="email" placeholder="Type your email" required>--%>
<%--        </div>--%>
        <div class="form-group">
            <div class="form-under"><fmt:message key="registerUsernameTitle"/></div>
            <label class="form-label" for="username"> </label>
            <input class="form-input" type="text" id="username" name="username" placeholder="Type username"
                   pattern="[a-zA-Z0-9]{1,40}" required>
        </div>
        <div class="form-group">
            <div class="form-under"><fmt:message key="registerPasswordTitle"/></div>
            <label for="password"></label>
            <input class="form-input" name="password" pattern="[A-za-z0-9]{1,40}" type="password" id="password"
                   placeholder="Type password" required>
        </div>
        <button type="button" id="register_btn" class="register-btn"><fmt:message key="registerBtn"/></button>
        <div class="text-span"> <fmt:message key="orSignUp"/></div>
        <div class="social-login">
            <a href="#" class="facebook">
            </a>
            <a href="#" class="twitter">
            </a>
            <a href="#" class="google">
            </a>
        </div>
    </form>
</div>
</body>
</html>