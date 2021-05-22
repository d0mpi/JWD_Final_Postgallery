<%--
  Created by IntelliJ IDEA.
  User: mdoku
  Date: 22.05.2021
  Time: 13:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Error</title>
    <link rel="stylesheet" type="text/css" href="css/index-styles.css">
    <link rel="stylesheet" type="text/css" href="css/error-styles.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="fonts/fonts.css">
    <jsp:include page="WEB-INF/title-logo.jsp"/>

    <script src="js/modelRegistration.js" defer></script>
    <script src="js/viewHeaderButtons.js" defer></script>
</head>
<body>
<jsp:include page="WEB-INF/header.jsp"/>


<main>
    <div class="main-container">
        <div class="main-col">
            <div class="error-text-box">
                <div class="error-number">404</div>
                <div class="error-text"> Oooops :( The page you are looking for was not found.</div>

            </div>
            <div class="push"></div>
        </div>
    </div>

</main>

<jsp:include page="WEB-INF/footer.jsp"/>


</body>
</html>
