<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${cookie['language'].value}" scope="session"/>
<fmt:setBundle basename="text"/>
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <title>Up! Post gallery.</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="../../css/index-styles.css">
    <link rel="stylesheet" type="text/css" href="../../fonts/fonts.css">
    <script src="https://kit.fontawesome.com/ede64561b8.js" crossorigin="anonymous"></script>

    <script src="../../js/jquery-3.6.0.min.js"></script>
    <script src="../../js/home.js"></script>
    <jsp:include page="title-logo.jsp"/>
</head>
<body>
<jsp:include page="header.jsp"/>
<main>

    <div class="main-container">
        <div class="filter-col">
            <form class="filter-fieldset" name="filter" method="post"
                  action="${pageContext.request.contextPath}/controller?command=main_page">
                <div class="filter-head"><fmt:message key="filterTitle"/></div>
                <ul class="filter-ul">
                    <li>
                        <label class="filter-label">
                            <div>
                                <%--                                <input id="filter_date_check" name="checkbox" value="filter_date_checkbox"--%>
                                <%--                                       type="checkbox" class="filter-checkbox">--%>
                                <span><fmt:message key="filterDateTitle"/></span>
                            </div>
                            <input id="filter_date" name="filter_date_text" type="date"
                                   class="filter-input">
                        </label>
                    </li>
                    <li>
                        <label class="filter-label">
                            <div>
                                <%--                                <input id="filter_author_check" name="checkbox" value="filter_author_checkbox"--%>
                                <%--                                       type="checkbox" class="filter-checkbox">--%>
                                <span><fmt:message key="filterAuthorTitle"/></span>
                            </div>
                            <input id="filter_author" name="filter_author_text" type="text" class="filter-input"
                                   placeholder="<fmt:message key="filterAuthorInput"/>">
                        </label>
                    </li>
                    <li>
                        <label class="filter-label">
                            <div>
                                <%--                                <input name="checkbox" id="filter_hashtags_check" value="filter_hashtags_checkbox"--%>
                                <%--                                       type="checkbox" class="filter-checkbox">--%>
                                <span><fmt:message key="filterHashtagTitle"/></span>
                            </div>
                            <input id="filter_hashtag" name="filter_hashtags_text" type="text" class="filter-input"
                                   placeholder="<fmt:message key="filterHashtagInput"/>">
                        </label>
                    </li>
                    <li>
                        <input type="submit" id="filter_btn" class="filter-button"
                               value="<fmt:message key="filterBtn"/>">
                    </li>
                </ul>
            </form>
        </div>
        <div class="main-col">
            <div class="main-box-inline-block" id="post_container">
                <c:forEach var="post" items="${requestScope.postList}">
                    <div class="card-box">
                        <div class="card-main-col">
                            <div class="card-main-box col-full">
                                <div class="card-img-box">
                                    <img src="${post.photoLink}" alt="samolet" class="card-img">
                                </div>
                                <div class="card-text-box">
                                    <ul class="card-text-top font-card">
                                        <li><span><fmt:message
                                                key="postModel"/>:</span> ${post.model == "" ? "-" : post.model}  </li>
                                        <li><span><fmt:message
                                                key="postType"/>:</span> ${post.type == "" ? "-" : post.type}</li>
                                        <li><span><fmt:message
                                                key="postLength"/>:</span> ${post.length == 0 ? "-" : post.length}</li>
                                        <li><span><fmt:message
                                                key="postWingspan"/>:</span> ${post.wingspan == 0 ? "-" : post.wingspan}
                                        </li>
                                        <li><span><fmt:message
                                                key="postHeight"/>:</span> ${post.height == 0 ? "-" : post.height}</li>
                                        <li><span><fmt:message
                                                key="postOrigin"/>:</span> ${post.origin == "" ? "-" : post.origin}</li>
                                        <li><span><fmt:message
                                                key="postCrew"/>:</span> ${post.crew == 0 ? "-" : post.crew}</li>
                                        <li><span><fmt:message
                                                key="postMaxSpeed"/>:</span> ${post.speed == 0 ? "-" : post.speed} km/h
                                        </li>
                                        <li><span><fmt:message
                                                key="postFlyingDist"/>:</span> ${post.distance == 0 ? "-" : post.distance}
                                            km
                                        </li>
                                        <li><span><fmt:message
                                                key="postPrice"/>:</span> ${post.price == 0 ? "-" : post.price}$
                                        </li>
                                        <li>
                                            <hr class="card-text-hr col-full">
                                        </li>
                                    </ul>
                                    <div class="card-text-bottom-box">
                                        <ul class="card-text-bottom-left">
                                            <li class="card-text-user"> ${post.author} </li>
                                            <li class="card-id"><fmt:message key="id"/>: ${post.id}</li>
                                            <li class="card-text-time"> ${post.createdAt}</li>
                                        </ul>
                                        <c:if test="${sessionScope.user_name != null}">
                                            <input id="like-check${post.id}" class="like-check"
                                                   type="checkbox" ${true ? "checked" : "unchecked"}
                                                   value="${post.id}">
                                            <label for="like-check${post.id}" class="like-label">
                                                <i class="fas fa-heart fa-3x"></i>
                                            </label>
                                        </c:if>
                                    </div>

                                </div>
                            </div>

                            <div class="card-hashtags col-full">
                                    ${post.hashtagsAsHashString}
                            </div>
                        </div>

                        <div class="card-buttons-col">
                            <c:if test="${sessionScope.user_name != null}">
                                <c:if test="${sessionScope.user_name == post.author}">
                                    <a class="fas fa-edit a-icon"
                                       href="${pageContext.request.contextPath}/controller?command=post_edit_page&post_id=${post.id}">
                                    </a>
                                    <button class="fas fa-trash-alt a-icon delete-post" value="${post.id}">
                                    </button>
                                </c:if>
                            </c:if>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div class="more-button-box">
                <button class="more-button" id="more_btn">
                    <div class="fa fa-refresh"></div>
                    <fmt:message key="loadMore"/>
                </button>
            </div>
        </div>
    </div>
</main>

<jsp:include page="footer.jsp"/>

</body>
</html>