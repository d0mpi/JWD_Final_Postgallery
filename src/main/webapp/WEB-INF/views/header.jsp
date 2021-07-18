<%@ page contentType="text/html;charset=UTF-8" %>
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
                <a class="header-logo-ref" href="${pageContext.request.contextPath}/controller?command=main_page">
                    <img class="img-logo" src="../../images/logo-white.png" alt="header-logo">
                </a>
            </div>
            <nav class="header-menu">
                <div class="menu-col ">
                    <a href="${pageContext.request.contextPath}/controller?command=main_page" id="header-home-btn"
                       class="menu-href">
                        <div></div>
                        <fmt:message key="headerHome"/> </a>
                </div>
                <div class="menu-col">
                    <a href="${pageContext.request.contextPath}/controller?command=about_page" id="header-about-btn"
                       class="menu-href ">
                        <div></div>
                        <fmt:message key="headerAbout"/></a>
                </div>
                <div class="menu-col">
                    <a href="${pageContext.request.contextPath}/controller?command=contact_page" id="header-contact-btn"
                       class="menu-href">
                        <div></div>
                        <fmt:message key="headerContact"/></a>
                </div>
                <form class="menu-col" id="languageForm" method="post" action="${pageContext.request.contextPath}/controller?command=main_page">
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
                <c:if test="${sessionScope.user_name != null}">
                <button onclick="document.location='/controller?command=post_add_page'"
                        class="header-add-button" id="add_btn">
                        <fmt:message key="headerAddBtn"/>
                    </c:if>
            </div>

            <div class="header-log-col">
                <c:if test="${sessionScope.user_name != null}">
                    <div class="header-username-col" id="header_username"> User: ${sessionScope.user_name}</div>
                    <form name="log1" action="${pageContext.request.contextPath}/controller?command=sign_out"
                          method="post">
                        <input name="sign-out" type="submit" title="" class="header-button" id="sign_out_btn"
                               value="<fmt:message key="headerSignOutBtn"/>"/>
                    </form>
                </c:if>
                <c:if test="${sessionScope.user_name == null}">
                    <a href="${pageContext.request.contextPath}/controller?command=login_page" type="submit"
                       class="header-button" id="sign_in_btn1"><fmt:message key="headerSignInBtn"/></a>
                </c:if>
            </div>
        </div>
    </div>
</header>
</body>
</html>
