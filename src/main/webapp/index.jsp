<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Up! Post gallery.</title>
    <link rel="stylesheet" type="text/css" href="css/index-styles.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="fonts/fonts.css">
    <jsp:include page="WEB-INF/title-logo.jsp"/>
<%--    <script src="js/modelRegistration.js" defer></script>--%>
<%--    <script src="js/viewPost.js" defer></script>--%>
<%--    <script src="js/modelPosts.js" defer></script>--%>
<%--    <script src="js/controllerFeed.js" defer></script>--%>
<%--    <script src="js/viewHeaderButtons.js" defer></script>--%>

</head>
<body>
<jsp:include page="WEB-INF/header.jsp"/>
<main>
    <div class="main-container">
        <div class="filter-col">
            <form class="filter-fieldset" name="filter" method="post" action="${pageContext.request.contextPath}/filter">
                <div class="filter-head"> Filter by</div>
                <ul class="filter-ul">
                    <li>
                        <label class="filter-label">
                            <div>
                                <input name="checkbox" value="filter_date_checkbox" type="checkbox" class="filter-checkbox">
                                <span>Date</span>
                            </div>
                            <input name="filter_date_text" value='2021-05-23' type="date" class="filter-input" >
                        </label>
                    </li>
                    <li>
                        <label class="filter-label">
                            <div>
                                <input name="checkbox" value="filter_author_checkbox" type="checkbox" class="filter-checkbox">
                                <span>Author</span>
                            </div>
                            <input name="filter_author_text" type="text" class="filter-input"
                                   placeholder="Username">
                        </label>
                    </li>
                    <li>
                        <label class="filter-label">
                            <div>
                                <input name="checkbox" value="filter_hashtags_checkbox" type="checkbox" class="filter-checkbox"
                                       placeholder="Hashtag">
                                <span>Hashtag</span>
                            </div>
                            <input name="filter_hashtags_text" type="text" class="filter-input"
                                   placeholder="Hashtag">
                        </label>
                    </li>
                    <li>
                        <button type="submit" id="filter_btn" class="filter-button">filter</button>
                    </li>
                </ul>
            </form>
        </div>
        <div class="main-col">
            <div class="main-box-inline-block">
                <c:forEach var="post" items="${requestScope.postList}">
                    <div class="card-box">
                        <div class="card-main-col">
                            <div class="card-main-box col-full">
                                <div class="card-img-box">
                                    <img src="${post.photoLink}" alt="samolet" class="card-img">
                                </div>
                                <div class="card-text-box">
                                    <ul class="card-text-top font-card">
                                        <li><span>Model:</span> ${post.model == "" ? "-" : post.model}  </li>
                                        <li><span>Type:</span> ${post.type == "" ? "-" : post.type}</li>
                                        <li><span>Length:</span> ${post.length == 0 ? "-" : post.length}</li>
                                        <li><span>Wingspan:</span> ${post.wingspan == 0 ? "-" : post.wingspan}</li>
                                        <li><span>Height:</span> ${post.height == 0 ? "-" : post.height}</li>
                                        <li><span>Origin:</span> ${post.origin == "" ? "-" : post.origin}</li>
                                        <li><span>Crew:</span> ${post.crew == 0 ? "-" : post.crew}</li>
                                        <li><span>Max speed:</span> ${post.speed == 0 ? "-" : post.speed} km/h</li>
                                        <li><span>Flying dist:</span> ${post.distance == 0 ? "-" : post.distance} km
                                        </li>
                                        <li><span>Price:</span> ${post.price == 0 ? "-" : post.price}$</li>
                                        <li>
                                            <hr class="card-text-hr col-full">
                                        </li>
                                    </ul>
                                    <div class="card-text-bottom-box">
                                        <ul class="card-text-bottom-left">
                                            <li class="card-text-user"> ${post.author} </li>
                                            <li class="card-id"> id: ${post.id}</li>
                                            <li class="card-text-time"> ${post.createdAt}</li>
                                        </ul>
                                        <c:if test="${sessionScope.logged != null}">
                                            <c:if test="${sessionScope.logged == true}">
                                                <form id="like-form${post.id}"
                                                      action="${pageContext.request.contextPath}/like" method="post">
                                                    <input id="like-check${post.id}" class="like-check" type="checkbox" value="">
                                                    <label for="like-check${post.id}" class="like-label"></label>
                                                </form>
                                            </c:if>
                                        </c:if>
                                    </div>

                                </div>
                            </div>

                            <div class="card-hashtags col-full">
                                    ${post.hashtagsAsString}
                            </div>
                        </div>

                        <div class="card-buttons-col">
                            <c:if test="${sessionScope.logged != null}">
                                <c:if test="${sessionScope.logged == true}">
                                    <c:if test="${sessionScope.login == post.author}">
                                        <form action="${pageContext.request.contextPath}/edit" method="get"
                                              name="editForm${post.id}">
                                            <button type="submit" name="edit" value="${post.id}"
                                                    class="card-edit-button" title=""></button>
                                        </form>
                                        <form action="${pageContext.request.contextPath}/home" method="post"
                                              name="deleteForm${post.id}">
                                            <button type="submit" name="delete" value="${post.id}"
                                                    class="card-delete-button" title=""></button>
                                        </form>
                                    </c:if>
                                </c:if>
                            </c:if>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div class="more-button-box">
                <button class="more-button" id="more_btn">
                    <div class="fa fa-refresh"></div>
                    LOAD MORE
                </button>
            </div>

        </div>
    </div>
</main>

<jsp:include page="WEB-INF/footer.jsp"/>

</body>
</html>