<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="ctag" uri="customTags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${cookie['language'].value}" scope="session"/>
<fmt:setBundle basename="text"/>
<%@ page import="by.bsu.d0mpi.UP_PostGallery.model.Role" %>
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <title>Up! Post gallery.</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>
    <link rel="stylesheet" type="text/css" href="../../css/index-styles.css">
    <link rel="stylesheet" type="text/css" href="../../fonts/fonts.css">
    <script src="https://kit.fontawesome.com/ede64561b8.js" crossorigin="anonymous"></script>

    <script src="../../js/jquery-3.6.0.min.js"></script>
    <script src="../../js/home.js" defer></script>
    <jsp:include page="title-logo.jsp"/>
</head>
<body>
<jsp:include page="header.jsp"/>
<main>
    <div class="overlay" hidden></div>
    <div class="main-container">
        <div class="mg-b-120">
            <div class="filter-col">
                <form class="filter-fieldset" name="filter" method="post"
                      action="${pageContext.request.contextPath}/controller?command=main_page">
                    <div class="filter-head"><fmt:message key="filterTitle"/></div>
                    <ul class="filter-ul">
                        <li>
                            <label class="filter-label">
                                <div>
                                    <span><fmt:message key="filterDateTitle"/></span>
                                </div>
                                <input id="filter_date" name="filter_date_text" type="date"
                                       class="filter-input" value="${requestScope.filter_date_text}">
                            </label>
                        </li>
                        <li>
                            <label class="filter-label">
                                <div>
                                    <span><fmt:message key="filterAuthorTitle"/></span>
                                </div>
                                <input id="filter_author" name="filter_author_text" type="text" class="filter-input"
                                       placeholder="<fmt:message key="filterAuthorInput"/>"
                                       value="${requestScope.filter_author_text}">
                            </label>
                        </li>
                        <li>
                            <label class="filter-label">
                                <div>
                                    <span><fmt:message key="filterHashtagTitle"/></span>
                                </div>
                                <input id="filter_hashtag" name="filter_hashtags_text" type="text" class="filter-input"
                                       placeholder="<fmt:message key="filterHashtagInput"/>"
                                       value="${requestScope.filter_hashtags_text}">
                            </label>
                        </li>
                        <li class="li-filter-button">
                            <input type="submit" id="filter_btn" class="filter-button"
                                   value="<fmt:message key="filterBtn"/>">
                            <button title="<fmt:message key="filterResetTitle"/>" class="filter-reset-button">
                                <i class="fas fa-sync" id="fa_sync"></i>
                            </button>
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
                                        <img src="/files/${post.id}-card.jpg" alt="plane" class="card-img resize"
                                             title="click to expand the image">
                                    </div>
                                    <div class="card-text-box">
                                        <ul class="card-text-top font-card">
                                            <li><span><fmt:message
                                                    key="postModel"/>:</span> ${post.model == "" ? "-" : post.model}
                                            </li>
                                            <li><span><fmt:message
                                                    key="postType"/>:</span> ${post.type == "" ? "-" : post.type}</li>
                                            <li><span><fmt:message
                                                    key="postLength"/>:</span> ${post.length == 0 ? "-" : post.length}
                                                <fmt:message key="MPostfix"/>
                                            </li>
                                            <li><span><fmt:message
                                                    key="postWingspan"/>:</span> ${post.wingspan == 0 ? "-" : post.wingspan}
                                                <fmt:message key="MPostfix"/>
                                            </li>
                                            <li><span><fmt:message
                                                    key="postHeight"/>:</span> ${post.height == 0 ? "-" : post.height}
                                                <fmt:message key="MPostfix"/>
                                            </li>
                                            <li><span><fmt:message
                                                    key="postOrigin"/>:</span> ${post.origin == "" ? "-" : post.origin}
                                            </li>
                                            <li><span><fmt:message
                                                    key="postCrew"/>:</span> ${post.crew == 0 ? "-" : post.crew}</li>
                                            <li><span><fmt:message
                                                    key="postMaxSpeed"/>:</span> ${post.speed == 0 ? "-" : post.speed}
                                                <fmt:message key="KmHPostfix"/>
                                            </li>
                                            <li><span><fmt:message
                                                    key="postFlyingDist"/>:</span> ${post.distance == 0 ? "-" : post.distance}
                                                <fmt:message key="KmPostfix"/>
                                            </li>
                                            <li><span><fmt:message
                                                    key="postPrice"/>:</span> ${post.price == 0 ? "-" : post.price}$
                                            </li>

                                        </ul>
                                        <hr class="card-text-hr col-full">
                                        <div class="card-text-bottom-box">
                                            <ul class="card-text-bottom-left">
                                                <li> ${post.author} </li>
                                                <li><fmt:message key="id"/>: ${post.id}</li>
                                                <li>
                                                        <ctag:locale-date date="${post.createdDate}"/>
                                                <li>
                                            </ul>
                                            <c:if test="${sessionScope.user_name != null}">
                                                <input id="like-check${post.id}" class="like-check"
                                                       type="checkbox" ${requestScope.likedPostsIdList.contains(post.id) ? "checked" : "unchecked"}
                                                       value="${post.id}">
                                                <label for="like-check${post.id}" class="like-label">
                                                    <i class="fas fa-heart"></i>
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
                                    <c:if test="${sessionScope.user_name == post.author || sessionScope.current_role.name().equals('ADMIN')
                                    || sessionScope.current_role.name().equals('MODERATOR')}">
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
                    <div class="pagination-bar col-full">
                        <c:choose>
                            <c:when test="${requestScope.postList.isEmpty()}">
                                <p class="no-post-text">
                                    <fmt:message key="noPostsText"/>
                                </p>
                            </c:when>
                            <c:otherwise>
                                <c:set var="filter_postfix"
                                       value="&filter_hashtags_text=${requestScope.filter_hashtags_text}&filter_author_text=${requestScope.filter_author_text}&filter_date_text=${requestScope.filter_date_text}"/>
                                <c:if test="${requestScope.pageNumber>1}">
                                    <a href="${pageContext.request.contextPath}/controller?command=main_page&page_number=${requestScope.pageNumber-1}${filter_postfix}"><i
                                            class="fas fa-angle-double-left"></i></a>
                                </c:if>
                                <c:forEach var="i"
                                           begin="${requestScope.pageNumber-4>=1 ? requestScope.pageNumber-4 : 1}"
                                           end="${requestScope.pageNumber+4>requestScope.pageCount ? requestScope.pageCount : requestScope.pageNumber+4}">
                                    <a href="${pageContext.request.contextPath}/controller?command=main_page&page_number=${i}${filter_postfix}"
                                       class="${requestScope.pageNumber == i ? "active" : ""}">${i}</a>
                                </c:forEach>
                                <c:if test="${requestScope.pageNumber<requestScope.pageCount}">
                                    <a href="${pageContext.request.contextPath}/controller?command=main_page&page_number=${requestScope.pageNumber+1}${filter_postfix}"><i
                                            class="fas fa-angle-double-right"></i></a>
                                </c:if>
                                <div>
                                    of ${requestScope.pageCount}
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>

<jsp:include page="footer.jsp"/>

</body>
</html>