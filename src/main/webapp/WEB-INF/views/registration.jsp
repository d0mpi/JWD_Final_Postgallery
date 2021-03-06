<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${cookie['language'].value}" scope="session"/>
<fmt:setBundle basename="text"/>
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <title>Register</title>
    <link rel="stylesheet" type="text/css" href="../../css/sign-styles.css">
    <script src="https://kit.fontawesome.com/ede64561b8.js" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="../../fonts/fonts.css">
    <script src="../../js/jquery-3.6.0.min.js"></script>

    <jsp:include page="title-logo.jsp"/>
</head>
<body>
<div class="sign-in-box">
    <a class="logo-row" href="${pageContext.request.contextPath}/controller?command=main_page">
        <img src="../../images/logo-white.png" alt="logo">
    </a>
    <div class="text-top-row">
        <h3><fmt:message key="registerTitle"/></h3>
    </div>
    <c:if test="${not empty requestScope.error_text}">
        <p class="registration-error">
                ${requestScope.error_text}
        </p>
    </c:if>
    <form class="form-row" method="post" name="reg_form"
          action="${pageContext.request.contextPath}/controller?command=register">
        <div class="form-group">
            <div class="form-under"><fmt:message key="registerUsernameTitle"/></div>
            <label class="form-label" for="login"> </label>
            <input class="form-input" type="text" id="login" name="login" placeholder=<fmt:message key="registerUsernameInput"/>
                   pattern="[a-zA-Z0-9]{1,40}" required>
        </div>
        <div class="form-group">
            <div class="form-under"><fmt:message key="registerPasswordTitle"/></div>
            <label for="password"></label>
            <input class="form-input" name="password" pattern="[A-za-z0-9]{1,40}" type="password" id="password"
                   placeholder=<fmt:message key="registerPasswordInput"/> required>
        </div>
        <input type="submit" id="register_btn" class="sign-in-btn" value="<fmt:message key="registerBtn"/>">
        <div class="text-span"><fmt:message key="orSignUp"/></div>

        <div class="social-login">
            <a href="#" class="fab fa-facebook-f facebook"></a>
            <a href="#" class="twitter fab fa-twitter"></a>
            <a href="#" class="google fab fa-google"></a>
        </div>
    </form>
</div>
</body>
</html>