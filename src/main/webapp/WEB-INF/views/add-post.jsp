<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${cookie['language'].value}" scope="session"/>
<fmt:setBundle basename="text"/>
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Add post</title>
    <link rel="stylesheet" type="text/css" href="../../css/index-styles.css">
    <link rel="stylesheet" type="text/css" href="../../css/add-post-styles.css">
    <script src="https://kit.fontawesome.com/ede64561b8.js" crossorigin="anonymous"></script>
    <link rel="stylesheet" type="text/css" href="../../fonts/fonts.css">
    <script src="../../js/controllerAdd.js" defer></script>
    <script src="../../js/jquery-3.6.0.min.js"></script>
    <jsp:include page="title-logo.jsp"/>
</head>
<body>
<jsp:include page="header.jsp"/>

<main>
    <div class="main-container">
        <div class="main-col">
            <div class="col-66">
                <form enctype="multipart/form-data" name="add" method="post"
                      action="${pageContext.request.contextPath}/controller?command=add_post"
                      class="add-main-box">
                    <div class="col-full add-title">
                        <h3><fmt:message key="addLabel"/></h3>
                    </div>
                    <div class="col-half" style="height: 504px">
                        <div class="img-box col-full">
                            <div class="add-label"><fmt:message key="editimage"/> 1:1 ratio recommended</div>
                            <div id="drag-and-drop">
                                <img src="" alt="" class="add-img" id="add_img">
                                <div class="drag-text" id="drag-text">
                                    <i class="fas fa-download fa-8x"></i>
                                    <div>
                                        <label class="drop-button">Choose a file
                                            <input type="file" name="file" id="file" class="input-file"
                                                   accept="image/jpeg,image/png,image/jpg" required>
                                        </label>
                                        &#160or drag it here
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                    <div class="col-half">
                        <div class="add-text-box">
                            <div class="add-form">
                                <div class="row">
                                    <div class="col-full">
                                        <div class="input-box">
                                            <label title="This field is mandatory" for="add-model" class="add-label">
                                                <fmt:message key="postModel"/>*</label>
                                            <input id="add-model" name="model" type="text" class="input-text" required
                                                   maxlength="100">
                                        </div>
                                    </div>
                                    <div class="col-33">
                                        <div class="input-box">
                                            <label for="add-height" class="add-label"> <fmt:message key="postHeight"/>(m)</label>
                                            <input id="add-height" name="height" type="text" class="input-text"
                                                   pattern="[0-9]+([\.,][0-9]+)?">
                                        </div>
                                    </div>
                                    <div class="col-33">
                                        <div class="input-box">
                                            <label for="add-length" class="add-label"> <fmt:message key="postLength"/>(m)</label>
                                            <input id="add-length" type="text" name="lengthInput" class="input-text"
                                                   pattern="[0-9]+([\.,][0-9]+)?">
                                        </div>
                                    </div>
                                    <div class="col-33">
                                        <div class="input-box">
                                            <label for="add-wingspan" class="add-label"> <fmt:message
                                                    key="postWingspan"/>(m)</label>
                                            <input id="add-wingspan" type="text" name="wingspan" class="input-text"
                                                   pattern="[0-9]+([\.,][0-9]+)?">
                                        </div>
                                    </div>
                                    <div class="col-half">
                                        <div class="input-box">
                                            <label for="add-type" class="add-label"> <fmt:message
                                                    key="postType"/></label>
                                            <select id="add-type" name="type" type="text" class="input-text">
                                                <option>fighter</option>
                                                <option>transport</option>
                                                <option>maritime patrol</option>
                                                <option>bomber</option>
                                                <option>attack</option>
                                                <option>reconnaissance</option>
                                                <option>multirole</option>
                                                <option>airborne early warning</option>
                                                <option>experimental</option>
                                                <option>electronic warfare</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-half">
                                        <div class="input-box">
                                            <label for="add-crew" class="add-label"> <fmt:message
                                                    key="postCrew"/></label>
                                            <input id="add-crew" type="number" name="crew" class="input-text" min="0"
                                                   max="100" value="0">
                                        </div>
                                    </div>
                                    <div class="col-half">
                                        <div class="input-box">
                                            <label for="price" class="add-label" title="This field is mandatory">
                                                <fmt:message key="postPrice"/>($)</label>
                                            <input id="price" type="text" name="price" class="input-text" required
                                                   pattern="[0-9]+([\.,][0-9]+)*">
                                        </div>
                                    </div>
                                    <div class="col-half">
                                        <div class="input-box">
                                            <label for="add-origin" class="add-label"> <fmt:message
                                                    key="postOrigin"/></label>
                                            <input id="add-origin" type="text" name="origin" class="input-text"
                                                   maxlength="100">
                                        </div>
                                    </div>
                                    <div class="col-half">
                                        <div class="input-box">
                                            <label for="add-speed" class="add-label"> <fmt:message key="postMaxSpeed"/>
                                                (km/h)</label>
                                            <input id="add-speed" type="text" name="speed" class="input-text"
                                                   pattern="[0-9]+([\.,][0-9]+)?" maxlength="20">
                                        </div>
                                    </div>
                                    <div class="col-half">
                                        <div class="input-box">
                                            <label for="add-distance" class="add-label"> <fmt:message
                                                    key="postFlyingDist"/>(km)</label>
                                            <input id="add-distance" type="text" name="dist" class="input-text"
                                                   pattern="[0-9]+([\.,][0-9]+)?" maxlength="20">
                                        </div>
                                    </div>
                                    <div class="col-full">
                                        <div class="input-box">
                                            <label for="add-hashtags" class="add-label"> <fmt:message
                                                    key="editHashtags"/></label>
                                            <input id="add-hashtags" type="text" name="hashtags" class="input-text"
                                                   maxlength="100">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-full add-btn-box">
                        <button id="addButton" type="submit" class="add-btn">
                            <fmt:message key="sendBtn"/>
                        </button>
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