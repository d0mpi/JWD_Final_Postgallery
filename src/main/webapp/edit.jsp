<%--
  Created by IntelliJ IDEA.
  User: mdoku
  Date: 22.05.2021
  Time: 13:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <script src="js/viewEdit.js" defer></script>
    <script src="js/modelPosts.js" defer></script>
    <script src="js/controllerEdit.js" defer></script>
    <script src="js/viewHeaderButtons.js" defer></script>
</head>
<body>
<jsp:include page="WEB-INF/header.jsp"/>

<main>
    <div class="main-container">
        <div class="main-col">
            <div class="col-66">
                <form name="edit" onsubmit="return false;" method="post" class="edit-main-box">
                    <div class="col-full edit-title">
                        <h3>Edit information</h3>
                    </div>
                    <div class="col-half" style="height: 504px">
                        <div class="img-box col-full">
                            <div class="edit-label"> Image</div>
                            <div class="drag-and-drop">
                                <img src="" alt="" class="add-img" id="add_img">
                            </div>
                        </div>
                        <div class="col-full file-load-box">
                            <input type="file" id="dropbox" name="file" class="input-file">
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
                                                   maxlength="100">
                                        </div>
                                    </div>
                                    <div class="col-33">
                                        <div class="input-box">
                                            <label for="edit-height" class="edit-label"> Height(m)</label>
                                            <input id="edit-height" name="height" type="text" class="input-text"
                                                   pattern="[0-9]+([\.,][0-9]+)?">
                                        </div>
                                    </div>
                                    <div class="col-33">
                                        <div class="input-box">
                                            <label for="edit-length" class="edit-label"> Length(m)</label>
                                            <input id="edit-length" type="text" name="lengthInput" class="input-text"
                                                   pattern="[0-9]+([\.,][0-9]+)?">
                                        </div>
                                    </div>
                                    <div class="col-33">
                                        <div class="input-box">
                                            <label for="edit-wingspan" class="edit-label"> Wingspan(m)</label>
                                            <input id="edit-wingspan" type="text" name="wingspan" class="input-text"
                                                   pattern="[0-9]+([\.,][0-9]+)?">
                                        </div>
                                    </div>
                                    <div class="col-half">
                                        <div class="input-box">
                                            <label for="edit-type" class="edit-label"> Type</label>
                                            <select id="edit-type" name="type" class="input-text">
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
                                            <label for="edit-crew" class="edit-label"> Crew</label>
                                            <input id="edit-crew" type="number" name="crew" class="input-text" min="0"
                                                   max="100" value="0">
                                        </div>
                                    </div>
                                    <div class="col-half">
                                        <div class="input-box">
                                            <label for="price" class="edit-label" title="This field is mandatory"> Price
                                                ($)</label>
                                            <input id="price" type="text" name="price" class="input-text" required
                                                   pattern="[0-9]+([\.,][0-9]+)*">
                                        </div>
                                    </div>
                                    <div class="col-half">
                                        <div class="input-box">
                                            <label for="edit-origin" class="edit-label"> Origin</label>
                                            <input id="edit-origin" type="text" name="origin" class="input-text"
                                                   maxlength="100">
                                        </div>
                                    </div>
                                    <div class="col-half">
                                        <div class="input-box">
                                            <label for="edit-speed" class="edit-label"> Maximum speed (km/h)</label>
                                            <input id="edit-speed" type="text" name="speed" class="input-text"
                                                   pattern="[0-9]+([\.,][0-9]+)?" maxlength="20">
                                        </div>
                                    </div>
                                    <div class="col-half">
                                        <div class="input-box">
                                            <label for="edit-distance" class="edit-label"> Flying distance(km)</label>
                                            <input id="edit-distance" type="text" name="dist" class="input-text"
                                                   pattern="[0-9]+([\.,][0-9]+)?" maxlength="20">
                                        </div>
                                    </div>
                                    <div class="col-full">
                                        <div class="input-box">
                                            <label for="edit-hashtags" class="edit-label"> Hashtags</label>
                                            <input id="edit-hashtags" type="text" name="hashtags" class="input-text"
                                                   maxlength="200">
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