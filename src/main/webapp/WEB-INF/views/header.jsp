<%--
  Created by IntelliJ IDEA.
  User: mdoku
  Date: 22.05.2021
  Time: 14:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Title</title>
    <script src="../../js/headerActiveBtn.js" defer></script>
</head>
<body>
<header>
    <div class="header-flex-box">
        <div class="header-left">
            <div class="header-logo-col">
                <a class="header-logo-ref" href="${pageContext.request.contextPath}/home">
                    <img class="img-logo" src="../../images/logo-white.png" alt="header-logo">
                </a>
            </div>
            <nav class="header-menu">
                <div class="menu-col ">
                    <a href="${pageContext.request.contextPath}/home" id="header-home-btn" class="menu-href">
                        <div></div>
                        HOME</a>
                </div>
                <div class="menu-col">
                    <a href="${pageContext.request.contextPath}/about" id="header-about-btn" class="menu-href ">
                        <div></div>
                        ABOUT</a>
                </div>
                <div class="menu-col">
                    <a href="${pageContext.request.contextPath}/contact" id="header-contact-btn" class="menu-href">
                        <div></div>
                        CONTACT US</a>
                </div>
                <div class="menu-col">
                    <label for="language-select"></label>
                    <select id="language-select" class="language-select">
                        <option>RU</option>
                        <option>EN</option>
                        <option>CH</option>
                    </select>
                </div>
            </nav>
        </div>
        <div class="header-right">
            <div class="header-add-col">
                <c:if test="${sessionScope.logged != null}">
                    <c:if test="${sessionScope.logged == true}">
                        <button onclick="document.location='/add-post'" class="header-add-button" id="add_btn">
                            ADD POST
                        </button>
                    </c:if>
                </c:if>
            </div>

            <div class="header-log-col">
                <c:if test="${sessionScope.logged != null}">
                    <c:if test="${sessionScope.logged == true}">
                        <div class="header-username-col" id="header_username"> User: ${sessionScope.login}</div>
                        <form name="log1" action="${pageContext.request.contextPath}/home"
                              method="post">
                            <button name="sign-out" type="submit" title="" class="header-button" id="sign_out_btn"
                                    value=""> SIGN
                                OUT
                            </button>
                        </form>
                    </c:if>
                </c:if>
                <c:if test="${sessionScope.logged != null}">
                    <c:if test="${sessionScope.logged == false}">
                        <form name="log2" action="${pageContext.request.contextPath}/sign"
                              method="get">
                            <button name="sign-in" type="submit" title="" value="" class="header-button"
                                    id="sign_in_btn1"> SIGN IN
                            </button>
                        </form>
                    </c:if>
                </c:if>
                <c:if test="${sessionScope.logged == null}">
                    <form name="log3" action="${pageContext.request.contextPath}/sign"
                          method="get">
                        <button name="sign-in" type="submit" title="" value="" class="header-button" id="sign_in_btn2">
                            SIGN IN
                        </button>
                    </form>
                </c:if>
            </div>
        </div>
    </div>
</header>
</body>
</html>
