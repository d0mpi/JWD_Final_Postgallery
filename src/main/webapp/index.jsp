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
    <script src="js/modelRegistration.js" defer></script>
    <script src="js/viewPost.js" defer></script>
    <script src="js/modelPosts.js" defer></script>
    <script src="js/controllerFeed.js" defer></script>
    <script src="js/viewHeaderButtons.js" defer></script>

</head>
<body>
<jsp:include page="WEB-INF/header.jsp"/>
<main>
    <div class="main-container">
        <div class="filter-col">
            <form class="filter-fieldset" name="filter">
                <div class="filter-head"> Filter by</div>
                <ul class="filter-ul">
                    <li>
                        <label class="filter-label">
                            <div>
                                <input name="filter_date_checkbox" type="checkbox" class="filter-checkbox">
                                <span>Date</span>
                            </div>
                            <input name="filter_date_text" value='2021-03-26' type="date" class="filter-input">
                        </label>
                    </li>
                    <li>
                        <label class="filter-label">
                            <div>
                                <input name="filter_author_checkbox" type="checkbox" class="filter-checkbox">
                                <span>Author</span>
                            </div>
                            <input name="filter_author_text" type="text" class="filter-input" placeholder="Username">
                        </label>
                    </li>
                    <li>
                        <label class="filter-label">
                            <div>
                                <input name="filter_hashtags_checkbox" type="checkbox" class="filter-checkbox"
                                       placeholder="Hashtag">
                                <span>Hashtag</span>
                            </div>
                            <input name="filter_hashtags_text" type="text" class="filter-input" placeholder="Hashtag">
                        </label>
                    </li>
                    <li>
                        <button type="button" id="filter_btn" class="filter-button">filter</button>
                    </li>
                </ul>
            </form>
        </div>
        <div class="main-col">
            <div class="main-box-inline-block">
                <c:forEach var="post" items="${requestScope.postList}">
                    <div class="card-main-col">
                        <div class="card-main-box col-full">
                            <div class="card-img-box">
                                <img src="${post.photoLink}" alt="samolet" class="card-img">
                            </div>
                            <div class="card-text-box">
                                <ul class="card-text-top font-card">
                                    <li><span>Model:</span> ${post.model}  </li>
                                    <li><span>Type:</span> ${post.type}</li>
                                    <li><span>Length:</span> ${post.length}</li>
                                    <li><span>Wingspan:</span> ${post.wingspan}</li>
                                    <li><span>Height:</span> ${post.height}</li>
                                    <li><span>Origin:</span> ${post.origin}</li>
                                    <li><span>Crew:</span> ${post.crew}</li>
                                    <li><span>Max speed:</span> ${post.speed} km/h</li>
                                    <li><span>Flying dist:</span> ${post.distance} km</li>
                                    <li><span>Price:</span> ${post.price}$</li>
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

<%--                                    <input id="${likeID}" class="like-check ${likeHide}" type="checkbox" ${checked} >--%>
<%--                                    <label for="like-check" class="like-label ${likeHide}"></label>--%>
                                </div>

                            </div>
                        </div>

                        <div class="card-hashtags col-full">
                                ${post.hashtags}
                        </div>
                    </div>

<%--                    <div class="card-buttons-col ${hide}">--%>
<%--                        <button  class="card-edit-button"> </button>--%>
<%--                        <button  class="card-delete-button" ></button>--%>
<%--                    </div>--%>
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

<c:if test="${sessionScope.logged != null}">
    <c:if test="${sessionScope.logged == true}">
        <jsp:include page="WEB-INF/footer.jsp"/>
    </c:if>
</c:if>

</body>
</html>