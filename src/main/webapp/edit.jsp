<%--
  Created by IntelliJ IDEA.
  User: mdoku
  Date: 22.05.2021
  Time: 13:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Edit post</title>
    <link rel="stylesheet" type="text/css" href="css/index-styles.css">
    <link rel="stylesheet" type="text/css" href="css/edit-styles.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="fonts/fonts.css">
    <jsp:include page="WEB-INF/title-logo.jsp"/>

    <script src="js/modelRegistration.js" defer></script>
    <%--    <script src="js/viewEdit.js" defer></script>--%>
    <%--    <script src="js/modelPosts.js" defer></script>--%>
    <%--    <script src="js/controllerEdit.js" defer></script>--%>
    <script src="js/viewHeaderButtons.js" defer></script>
</head>
<body>
<jsp:include page="WEB-INF/header.jsp"/>

<main>
    <div class="main-container">
        <div class="main-col">
            <div class="col-66">
                <form name="edit" method="post" action="${pageContext.request.contextPath}/edit" class="edit-main-box">
                    <div class="col-full edit-title">
                        <h3>Edit information</h3>
                    </div>
                    <div class="col-half" style="height: 504px">
                        <div class="img-box col-full">
                            <div class="edit-label"> Image:
                            </div>
                            <div class="drag-and-drop">
                                <img src="${requestScope.postToEdit.photoLink}" alt="" class="add-img" id="add_img">
                                <label hidden>
                                    <input value="${requestScope.postToEdit.photoLink}" name="file" hidden>
                                </label>
                            </div>
                        </div>
                        <div class="col-full file-load-box">
<%--                            <input type="file" id="dropbox" name="file" class="input-file">--%>
                        </div>

                    </div>
                    <div class="col-half">
                        <div class="edit-text-box">
                            <div class="edit-form">
                                <div class="row">
                                    <div class="col-full">
                                        <div class="input-box">
                                            <label title="This field is mandatory" for="edit-model" class="edit-label">
                                                Model*</label>
                                            <input id="edit-model" name="model" type="text" class="input-text" required
                                                   maxlength="100" value="${requestScope.postToEdit.model}">
                                        </div>
                                    </div>
                                    <div class="col-33">
                                        <div class="input-box">
                                            <label for="edit-height" class="edit-label"> Height(m)</label>
                                            <input id="edit-height" name="height" type="text" class="input-text"
                                                   pattern="[0-9]+([\.,][0-9]+)?"
                                                   value="${requestScope.postToEdit.height}">
                                        </div>
                                    </div>
                                    <div class="col-33">
                                        <div class="input-box">
                                            <label for="edit-length" class="edit-label"> Length(m)</label>
                                            <input id="edit-length" type="text" name="lengthInput" class="input-text"
                                                   pattern="[0-9]+([\.,][0-9]+)?"
                                                   value="${requestScope.postToEdit.length}">
                                        </div>
                                    </div>
                                    <div class="col-33">
                                        <div class="input-box">
                                            <label for="edit-wingspan" class="edit-label"> Wingspan(m)</label>
                                            <input id="edit-wingspan" type="text" name="wingspan" class="input-text"
                                                   pattern="[0-9]+([\.,][0-9]+)?"
                                                   value="${requestScope.postToEdit.wingspan}">
                                        </div>
                                    </div>
                                    <div class="col-half">
                                        <div class="input-box">
                                            <label for="edit-type" class="edit-label"> Type</label>
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
                                            <label for="edit-crew" class="edit-label"> Crew</label>
                                            <input id="edit-crew" type="number" name="crew" class="input-text" min="0"
                                                   max="100" value="${requestScope.postToEdit.crew}">
                                        </div>
                                    </div>
                                    <div class="col-half">
                                        <div class="input-box">
                                            <label for="price" class="edit-label" title="This field is mandatory"> Price
                                                ($)</label>
                                            <input id="price" type="text" name="price" class="input-text" required
                                                   pattern="[0-9]+([\.,][0-9]+)*"
                                                   value="${requestScope.postToEdit.price}">
                                        </div>
                                    </div>
                                    <div class="col-half">
                                        <div class="input-box">
                                            <label for="edit-origin" class="edit-label"> Origin</label>
                                            <input id="edit-origin" type="text" name="origin" class="input-text"
                                                   maxlength="100" value="${requestScope.postToEdit.origin}">
                                        </div>
                                    </div>
                                    <div class="col-half">
                                        <div class="input-box">
                                            <label for="edit-speed" class="edit-label"> Maximum speed (km/h)</label>
                                            <input id="edit-speed" type="text" name="speed" class="input-text"
                                                   pattern="[0-9]+([\.,][0-9]+)?" maxlength="20"
                                                   value="${requestScope.postToEdit.speed}">
                                        </div>
                                    </div>
                                    <div class="col-half">
                                        <div class="input-box">
                                            <label for="edit-distance" class="edit-label"> Flying distance(km)</label>
                                            <input id="edit-distance" type="text" name="dist" class="input-text"
                                                   pattern="[0-9]+([\.,][0-9]+)?" maxlength="20"
                                                   value="${requestScope.postToEdit.distance}">
                                        </div>
                                    </div>
                                    <div class="col-full">
                                        <div class="input-box">
                                            <label for="edit-hashtags" class="edit-label"> Hashtags(?, ?, ...)</label>
                                            <input id="edit-hashtags" type="text" name="hashtags" class="input-text"
                                                   maxlength="200" value="${requestScope.postToEdit.hashtagsAsString}">
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-full edit-btn-box">
                        <button id="editButton" type="submit" class="edit-btn">
                            Send
                        </button>
                    </div>
                </form>
                <div class="push"></div>
            </div>
        </div>
    </div>
</main>

<jsp:include page="WEB-INF/footer.jsp"/>

</body>
</html>