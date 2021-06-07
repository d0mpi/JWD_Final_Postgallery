<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${cookie['language'].value}" scope="session"/>
<fmt:setBundle basename="text"/>
<html>
<head>
    <title>header</title>
    <script src="../../js/jquery-3.6.0.min.js"></script>
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
                        <fmt:message key="headerHome"/> </a>
                </div>
                <div class="menu-col">
                    <a href="${pageContext.request.contextPath}/about" id="header-about-btn" class="menu-href ">
                        <div></div>
                        <fmt:message key="headerAbout"/></a>
                </div>
                <div class="menu-col">
                    <a href="${pageContext.request.contextPath}/contact" id="header-contact-btn" class="menu-href">
                        <div></div>
                        <fmt:message key="headerContact"/></a>
                </div>
                <form class="menu-col" id="languageForm" method="post" action="${pageContext.request.contextPath}/home">
                    <label for="language-select"></label>
                    <select id="language-select" name="language-select" class="language-select"
                            onchange="document.getElementById('languageForm').submit()">
                        <option <c:if test="${cookie['language'].value.equals('ru_BY')}"> selected </c:if>>
                            RU
                        </option>
                        <option <c:if test="${cookie['language'].value.equals('en_US')}"> selected </c:if>
                                <c:if test="${cookie['language'].value == null}"> selected </c:if>>
                            EN
                        </option>
                        <option <c:if test="${cookie['language'].value.equals('zh_CN')}"> selected </c:if>>
                            CN
                        </option>
                    </select>
                </form>
            </nav>
        </div>
        <div class="header-right">
            <div class="header-add-col">
                <c:if test="${sessionScope.logged != null}">
                    <c:if test="${sessionScope.logged == true}">
                        <button onclick="document.location='/add-post'" class="header-add-button" id="add_btn">
                            <fmt:message key="headerAddBtn"/>
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
                                    value="">
                                <fmt:message key="headerSignOutBtn"/>
                            </button>
                        </form>
                    </c:if>
                </c:if>
                <c:if test="${sessionScope.logged != null}">
                    <c:if test="${sessionScope.logged == false}">
                        <form name="log2" action="${pageContext.request.contextPath}/sign"
                              method="get">
                            <button name="sign-in" type="submit" title="" value="" class="header-button"
                                    id="sign_in_btn1">
                                <fmt:message key="headerSignInBtn"/>
                            </button>
                        </form>
                    </c:if>
                </c:if>
                <c:if test="${sessionScope.logged == null}">
                    <form name="log3" action="${pageContext.request.contextPath}/sign"
                          method="get">
                        <button name="sign-in" type="submit" title="" value="" class="header-button" id="sign_in_btn2">
                            <fmt:message key="headerSignInBtn"/>
                        </button>
                    </form>
                </c:if>
            </div>
        </div>
    </div>
</header>
</body>
</html>
