<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${cookie['language'].value}" scope="session"/>
<fmt:setBundle basename="text"/>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Profile</title>
    <link rel="stylesheet" type="text/css" href="../../css/index-styles.css">
    <link rel="stylesheet" type="text/css" href="../../css/contact-styles.css">
    <script src="https://kit.fontawesome.com/ede64561b8.js" crossorigin="anonymous"></script>
    <link rel="stylesheet" type="text/css" href="../../fonts/fonts.css">
    <script src="../../js/jquery-3.6.0.min.js"></script>
    <jsp:include page="title-logo.jsp"/>
</head>
<body>
<jsp:include page="header.jsp"/>
<main>
    <div class="main-container">
        <div class="main-col">
            <div class="col-50-1">
                <div class="contact-stripped-box">
                    <div class="contact-main-box">
                        <div class="contact-title col-full">
                            <h3>User profile</h3>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
<jsp:include page="footer.jsp"/>
</body>
</html>
