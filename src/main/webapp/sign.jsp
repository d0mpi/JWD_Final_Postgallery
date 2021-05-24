<%--
  Created by IntelliJ IDEA.
  User: mdoku
  Date: 22.05.2021
  Time: 13:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <title>Sign in</title>
    <link rel="stylesheet" type="text/css" href="css/sign-styles.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="fonts/fonts.css">
    <script src="js/jquery-3.6.0.min.js"></script>
    <script src="js/sign.js"></script>

    <jsp:include page="WEB-INF/title-logo.jsp"/>

    <%--    <script src="js/modelRegistration.js" defer></script>--%>
    <%--    <script src="js/controllerSign.js" defer></script>--%>
</head>
<body>
<div class="sign-in-box">
    <a class="logo-row" href="${pageContext.request.contextPath}/home">
        <img src="images/logo-white.png" alt="logo">
    </a>
    <div class="text-top-row">
        <h3> Sign In</h3>
    </div>
    <form class="form-row" method="post" name="sign_form">
        <div class="form-group">
            <div class="form-under">Username</div>
            <label class="form-label" for="username"> </label>
            <input class="form-input" name="username" type="text" pattern="[a-zA-Z0-9]{1,40}" id="username"
                   placeholder="Type your username" required>
        </div>
        <div class="form-group">
            <div class="form-under">Password</div>
            <label for="password"></label>
            <input class="form-input" name="password" pattern="[A-za-z0-9]{1,40}" type="password" id="password"
                   placeholder="Type your password" required>
        </div>
        <a class="text-forgot">Forgot password ?</a>
        <button name="sign" type="button" id="sign_btn" class="sign-in-btn" value="">Sign in</button>
        <div class="text-span"> Or Sign Up Using</div>

        <div class="social-login">
            <a href="#" class="facebook">
            </a>
            <a href="#" class="twitter">
            </a>
            <a href="#" class="google">
            </a>
        </div>
    </form>
    <form method="get" action="${pageContext.request.contextPath}/registration" name="sign_form">
        <div class="text-create-row">
            <button name="register" class="a-create">Create your Account</button>
        </div>
    </form>
</div>
</body>
</html>