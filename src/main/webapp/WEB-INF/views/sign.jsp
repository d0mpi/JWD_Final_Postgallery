<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${cookie['language'].value}" scope="session"/>
<fmt:setBundle basename="text"/>
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <title>Sign in</title>
    <script src="https://kit.fontawesome.com/ede64561b8.js" crossorigin="anonymous"></script>
    <link rel="stylesheet" type="text/css" href="../../css/sign-styles.css">
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
        <h3><fmt:message key="signInTitle"/></h3>
    </div>
    <c:if test="${not empty requestScope.error_text}">
        <p class="sign-error">
                ${requestScope.error_text}
        </p>
    </c:if>
    <form class="form-row" method="post" name="sign_form"
          action="${pageContext.request.contextPath}/controller?command=sign_in">
        <div class="form-group">
            <div class="form-under"><fmt:message key="signInUsernameTitle"/></div>
            <label class="form-label" for="login"> </label>
            <input class="form-input" name="login" type="text" pattern="[a-zA-Z0-9]{1,40}" id="login"
                   placeholder="<fmt:message key="signInUsernameInput"/>" required>
        </div>
        <div class="form-group">
            <div class="form-under"><fmt:message key="signInPasswordTitle"/></div>
            <label for="password"></label>
            <input class="form-input" name="password" pattern="[A-za-z0-9]{1,40}" type="password" id="password"
                   placeholder="<fmt:message key="signInPasswordInput"/>" required>
        </div>
        <a class="text-forgot"><fmt:message key="signInForgot"/></a>
        <input name="sign" type="submit" id="sign_btn" class="sign-in-btn" value="<fmt:message key="headerSignInBtn"/>">
        <div class="text-span"><fmt:message key="orSignUp"/></div>

        <div class="social-login">
            <a href="#" class="fab fa-facebook-f facebook"></a>
            <a href="#" class="twitter fab fa-twitter"></a>
            <a href="#" class="google fab fa-google"></a>
        </div>
    </form>
    <div class="text-create-row">
        <a href="${pageContext.request.contextPath}/controller?command=registration_page"
           class="a-create"><fmt:message key="signInCreate"/></a>
    </div>
</div>
</body>
</html>