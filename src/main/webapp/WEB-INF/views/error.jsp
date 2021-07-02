<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Error</title>
    <link rel="stylesheet" type="text/css" href="../../css/index-styles.css">
    <link rel="stylesheet" type="text/css" href="../../css/error-styles.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="../../fonts/fonts.css">
    <jsp:include page="title-logo.jsp"/>
</head>
<body>
<jsp:include page="header.jsp"/>
<main>
    <div class="main-container">
        <div class="main-col">
            <div class="error-text-box">
                <div class="error-number" style="--before-content: '\f1e2'">
                    <c:if test="${requestScope.status_code != -1}">
                        ${requestScope.status_code}
                    </c:if>
                </div>
                <div class="error-text">
                    <c:choose>
                        <c:when test="${requestScope.status_code == 403}">
                            YOU SHALL NOT PASS!!!<br>
                            Maybe you have a typo in the url?<br>
                            Or you meant to go to a different location?
                        </c:when>
                        <c:when test="${requestScope.status_code == 404}">
                            Oooops :( The page you are looking for was not found.<br>
                            Maybe this page flew away on one of the planes from your feed;)
                        </c:when>
                        <c:when test="${requestScope.status_code == 500}">
                            A 500 error occurs when something blows up with our code.<br>
                            Viewing this does not fix this problem.<br>
                            In fact you are likely pissed off at the moment.<br>
                            Take solace, top men are now aware.
                        </c:when>
                        <c:when test="${requestScope.status_code == -1}">
                            ${requestScope.throwable.message}
                        </c:when>
                    </c:choose>
                </div>
            </div>
            <div class="push"></div>
        </div>
    </div>
</main>
<jsp:include page="footer.jsp"/>
</body>
</html>