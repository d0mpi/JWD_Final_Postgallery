<%--
  Created by IntelliJ IDEA.
  User: mdoku
  Date: 22.05.2021
  Time: 13:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${cookie['language'].value}" scope="session"/>
<fmt:setBundle basename="text"/>
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Edit post</title>
    <link rel="stylesheet" type="text/css" href="../../css/index-styles.css">
    <link rel="stylesheet" type="text/css" href="../../css/edit-styles.css">
    <script src="https://kit.fontawesome.com/ede64561b8.js" crossorigin="anonymous"></script>
    <link rel="stylesheet" type="text/css" href="../../fonts/fonts.css">
    <script src="../../js/controllerEdit.js" defer></script>
    <script src="../../js/jquery-3.6.0.min.js"></script>

    <jsp:include page="title-logo.jsp"/>
</head>
<body>
<jsp:include page="header.jsp"/>

<main>
    <div class="main-container">
        <div class="main-col">
            <div class="col-66">
                <form name="edit" enctype="multipart/form-data" method="post"
                      action="${pageContext.request.contextPath}/controller?command=edit_post"
                      class="edit-main-box">
                    <div class="col-full edit-title">
                        <h3><fmt:message key="editLabel"/></h3>
                    </div>
                    <div class="img-box col-full">
                        <div class="edit-label"><fmt:message key="editimage"/>:
                        </div>
                        <div class="drag-and-drop" id="drag-and-drop">
                            <img src="/files/${postToEdit.id}-card.jpg" alt="" class="add-img" id="add_img">
                        </div>
                    </div>
                    <div class="col-full image-text">
                        <label class="drop-button">Choose new file
                            <input type="file" name="file" id="file" class="input-file"
                                   accept="image/jpeg,image/png,image/jpg">
                        </label>
                        &#160or drag it here
                    </div>
                    <div class="col-half">
                        <div class="edit-text-box">
                            <div class="edit-form">
                                <div class="row">
                                    <div class="col-full">
                                        <div class="input-box">
                                            <label title="This field is mandatory" for="edit-model" class="edit-label">
                                                <fmt:message key="postModel"/>*</label>
                                            <input id="edit-model" name="model" type="text" class="input-text" required
                                                   maxlength="100" value="${requestScope.postToEdit.model}">

                                        </div>
                                    </div>
                                    <div class="col-33">
                                        <div class="input-box">
                                            <label for="edit-height" class="edit-label"> <fmt:message key="postHeight"/>(m)</label>
                                            <input id="edit-height" name="height" type="text" class="input-text"
                                                   pattern="[0-9]+([\.,][0-9]+)?"
                                                   value="${requestScope.postToEdit.height}">
                                        </div>
                                    </div>
                                    <div class="col-33">
                                        <div class="input-box">
                                            <label for="edit-length" class="edit-label"> <fmt:message key="postLength"/>(m)</label>
                                            <input id="edit-length" type="text" name="lengthInput" class="input-text"
                                                   pattern="[0-9]+([\.,][0-9]+)?"
                                                   value="${requestScope.postToEdit.length}">
                                        </div>
                                    </div>
                                    <div class="col-33">
                                        <div class="input-box">
                                            <label for="edit-wingspan" class="edit-label"> <fmt:message
                                                    key="postWingspan"/>(m)</label>
                                            <input id="edit-wingspan" type="text" name="wingspan" class="input-text"
                                                   pattern="[0-9]+([\.,][0-9]+)?"
                                                   value="${requestScope.postToEdit.wingspan}">
                                        </div>
                                    </div>
                                    <div class="col-half">
                                        <div class="input-box">
                                            <label for="edit-type" class="edit-label"> <fmt:message
                                                    key="postType"/></label>
                                            <select id="edit-type" name="type" class="input-text">
                                                <option ${requestScope.postToEdit.type.equals("fighter") ? "selected" : ""}>
                                                    fighter
                                                </option>
                                                <option ${requestScope.postToEdit.type.equals("transport") ? "selected" : ""}>
                                                    transport
                                                </option>
                                                <option ${requestScope.postToEdit.type.equals("maritime patrol") ? "selected" : ""}>
                                                    maritime patrol
                                                </option>
                                                <option ${requestScope.postToEdit.type.equals("bomber") ? "selected" : ""}>
                                                    bomber
                                                </option>
                                                <option ${requestScope.postToEdit.type.equals("attack") ? "selected" : ""}>
                                                    attack
                                                </option>
                                                <option ${requestScope.postToEdit.type.equals("reconnaissance") ? "selected" : ""}>
                                                    reconnaissance
                                                </option>
                                                <option ${requestScope.postToEdit.type.equals("multirole") ? "selected" : ""}>
                                                    multirole
                                                </option>
                                                <option ${requestScope.postToEdit.type.equals("airborne early warning") ? "selected" : ""}>
                                                    airborne early warning
                                                </option>
                                                <option ${requestScope.postToEdit.type.equals("experimental") ? "selected" : ""}>
                                                    experimental
                                                </option>
                                                <option ${requestScope.postToEdit.type.equals("electronic warfare") ? "selected" : ""}>
                                                    electronic warfare
                                                </option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-half">
                                        <div class="input-box">
                                            <label for="edit-crew" class="edit-label"> <fmt:message
                                                    key="postCrew"/></label>
                                            <input id="edit-crew" type="number" name="crew" class="input-text" min="0"
                                                   max="100" value="${requestScope.postToEdit.crew}">
                                        </div>
                                    </div>
                                    <div class="col-half">
                                        <div class="input-box">
                                            <label for="price" class="edit-label" title="This field is mandatory">
                                                <fmt:message key="postPrice"/>
                                                ($)</label>
                                            <input id="price" type="text" name="price" class="input-text" required
                                                   pattern="[0-9]+([\.,][0-9]+)*"
                                                   value="${requestScope.postToEdit.price}">
                                        </div>
                                    </div>
                                    <div class="col-half">
                                        <div class="input-box">
                                            <label for="edit-origin" class="edit-label"> <fmt:message
                                                    key="postOrigin"/></label>
                                            <input id="edit-origin" type="text" name="origin" class="input-text"
                                                   maxlength="100" value="${requestScope.postToEdit.origin}">
                                        </div>
                                    </div>
                                    <div class="col-half">
                                        <div class="input-box">
                                            <label for="edit-speed" class="edit-label"> <fmt:message
                                                    key="postMaxSpeed"/> (km/h)</label>
                                            <input id="edit-speed" type="text" name="speed" class="input-text"
                                                   pattern="[0-9]+([\.,][0-9]+)?" maxlength="20"
                                                   value="${requestScope.postToEdit.speed}">
                                        </div>
                                    </div>
                                    <div class="col-half">
                                        <div class="input-box">
                                            <label for="edit-distance" class="edit-label"> <fmt:message
                                                    key="postFlyingDist"/>(km)</label>
                                            <input id="edit-distance" type="text" name="dist" class="input-text"
                                                   pattern="[0-9]+([\.,][0-9]+)?" maxlength="20"
                                                   value="${requestScope.postToEdit.distance}">
                                        </div>
                                    </div>
                                    <div class="col-full">
                                        <div class="input-box">
                                            <label for="edit-hashtags" class="edit-label"> <fmt:message
                                                    key="editHashtags"/></label>
                                            <input id="edit-hashtags" type="text" name="hashtags" class="input-text"
                                                   maxlength="200"
                                                   value="${requestScope.postToEdit.hashtagsAsSpaceString}">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-full edit-btn-box">
                        <input id="editButton" type="submit" class="edit-btn" value="Update"/>
                    </div>
                </form>
                <div class="push"></div>
            </div>
        </div>
    </div>
</main>

<jsp:include page="footer.jsp"/>

</body>
</html>