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
    <link rel="stylesheet" type="text/css" href="../../css/profile-styles.css">
    <script src="https://kit.fontawesome.com/ede64561b8.js" crossorigin="anonymous"></script>
    <link rel="stylesheet" type="text/css" href="../../fonts/fonts.css">
    <script src="../../js/jquery-3.6.0.min.js"></script>
    <jsp:include page="title-logo.jsp"/>
</head>
<body>
<jsp:include page="header.jsp"/>
<main>
    <div class="main-col">
        <div class="col-50-1">
            <div class="profile-main-box">
                <div class="profile-title col-full">
                    Your profile, ${sessionScope.user_name}
                </div>
                <div class="col-full bordered">
                    <div class="col-33 column">
                        <i class="fas fa-paper-plane fa-2x"></i>
                        <p>${requestScope.number_of_posts}</p>
                        <p>Posts posted</p>
                    </div>
                    <div class="col-33 column">
                        <i class="fas fa-heart fa-2x"></i>
                        <p>${requestScope.number_of_likes}</p>
                        <p>Users' likes</p>
                    </div>
                    <div class="col-33 column">
                        <i class="fas fa-birthday-cake fa-2x "></i>
                        <p>${sessionScope.user_age}</p>
                        <p>Days on site</p>
                    </div>
                </div>
                <div class="email-area col-full">
                    <i class="fas fa-envelope fa-lg"></i>
                    mdokuchaevp@gmail.com
                </div>
                <form class="col-full change-area"
                      action="${pageContext.request.contextPath}/controller?command=change_password">
                    <label class="col-full" for="old_password">Enter current password: </label>
                    <input class="profile-input" pattern="[A-za-z0-9]{1,40}" type="password" id="old_password"
                           placeholder="Type your password" required>
                    <label class="col-full" for="new_password">Enter new password: </label>
                    <input class="profile-input" pattern="[A-za-z0-9]{1,40}" type="password" id="new_password"
                           placeholder="Type new password" required>
                    <input class="profile-button" type="submit" value="Change password">
                </form>
            </div>
        </div>
    </div>
    </div>
</main>
<jsp:include page="footer.jsp"/>
</body>
</html>
