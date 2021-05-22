<%--
  Created by IntelliJ IDEA.
  User: mdoku
  Date: 22.05.2021
  Time: 14:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="../js/headerActiveBtn.js" defer></script>
</head>
<body>
<header>
    <div class="header-flex-box">
        <div class="header-left">
            <div class="header-logo-col">
                <a class="header-logo-ref" href="../index.jsp">
                    <img class="img-logo" src="../images/logo-white.png" alt="header-logo">
                </a>
            </div>
            <nav class="header-menu">
                <div class="menu-col ">
                    <a href="../index.jsp" id="header-index.jsp-btn" class="menu-href">
                        <div></div>
                        HOME</a>
                </div>
                <div class="menu-col">
                    <a href="../about.jsp" id="header-about.jsp-btn" class="menu-href ">
                        <div></div>
                        ABOUT</a>
                </div>
                <div class="menu-col">
                    <a href="../contact.jsp" id="header-contact.jsp-btn" class="menu-href">
                        <div></div>
                        CONTACT US</a>
                </div>
                <div class="menu-col">
                    <a href="../error.jsp" id="header-error.jsp-btn" class="menu-href">
                        <div></div>
                        ERROR</a>
                </div>
            </nav>
        </div>
        <div class="header-right">
            <div class="header-add-col">
                <button onclick="document.location='add-post.jsp'" class="header-add-button hide" id="add_btn">
                    ADD POST
                </button>
            </div>

            <div class="header-log-col">
                <div class="header-username-col hide" id="header_username"> UserName</div>
                <button class="header-button" onclick="document.location='sign.jsp'" id="sign_in_btn"> SIGN IN</button>
                <button class="header-button" id="sign_out_btn"> SIGN OUT</button>
            </div>
        </div>
    </div>
</header>
</body>
</html>
