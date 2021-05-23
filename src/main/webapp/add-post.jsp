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
    <title>Add post</title>
    <link rel="stylesheet" type="text/css" href="css/index-styles.css">
    <link rel="stylesheet" type="text/css" href="css/add-post-styles.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="fonts/fonts.css">
    <jsp:include page="WEB-INF/title-logo.jsp"/>
    <script src="js/modelPosts.js" defer></script>
    <script src="js/modelRegistration.js" defer></script>
    <script src="js/controllerAdd.js" defer></script>
    <script src="js/viewHeaderButtons.js" defer></script>
</head>
<body>
<jsp:include page="WEB-INF/header.jsp"/>

<main>
    <div class="main-container">
        <div class="main-col">
            <div class="col-66">
                <form name="add" method="post" action="${pageContext.request.contextPath}/add-post" class="add-main-box">
                    <div class="col-full add-title">
                        <h3>Information</h3>
                    </div>
                    <div class="col-half" style="height: 504px">
                        <div class="img-box col-full">
                            <div class="add-label"> Image</div>
                            <div class="drag-and-drop">
                                <img src="" alt="" class="add-img" id="add_img">
                            </div>
                        </div>
                        <div class="col-full file-load-box">
                            <input type="file" name="file" id="dropbox" class="input-file"
                                   accept="image/jpeg,image/png,image/jpg" required>
                        </div>
                    </div>
                    <div class="col-half">
                        <div class="add-text-box">
                            <div class="add-form">
                                <div class="row">
                                    <div class="col-full">
                                        <div class="input-box">
                                            <label title="This field is mandatory" for="add-model" class="add-label">
                                                Model*</label>
                                            <input id="add-model" name="model" type="text" class="input-text" required
                                                   maxlength="100">
                                        </div>
                                    </div>
                                    <div class="col-33">
                                        <div class="input-box">
                                            <label for="add-height" class="add-label"> Height(m)</label>
                                            <input id="add-height" name="height" type="text" class="input-text"
                                                   pattern="[0-9]+([\.,][0-9]+)?">
                                        </div>
                                    </div>
                                    <div class="col-33">
                                        <div class="input-box">
                                            <label for="add-length" class="add-label"> Length(m)</label>
                                            <input id="add-length" type="text" name="lengthInput" class="input-text"
                                                   pattern="[0-9]+([\.,][0-9]+)?">
                                        </div>
                                    </div>
                                    <div class="col-33">
                                        <div class="input-box">
                                            <label for="add-wingspan" class="add-label"> Wingspan(m)</label>
                                            <input id="add-wingspan" type="text" name="wingspan" class="input-text"
                                                   pattern="[0-9]+([\.,][0-9]+)?">
                                        </div>
                                    </div>
                                    <div class="col-half">
                                        <div class="input-box">
                                            <label for="add-type" class="add-label"> Type</label>
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
                                            <label for="add-crew" class="add-label"> Crew</label>
                                            <input id="add-crew" type="number" name="crew" class="input-text" min="0"
                                                   max="100" value="0">
                                        </div>
                                    </div>
                                    <div class="col-half">
                                        <div class="input-box">
                                            <label for="price" class="add-label" title="This field is mandatory"> Price
                                                ($)</label>
                                            <input id="price" type="text" name="price" class="input-text" required
                                                   pattern="[0-9]+([\.,][0-9]+)*">
                                        </div>
                                    </div>
                                    <div class="col-half">
                                        <div class="input-box">
                                            <label for="add-origin" class="add-label"> Origin</label>
                                            <input id="add-origin" type="text" name="origin" class="input-text"
                                                   maxlength="100">
                                        </div>
                                    </div>
                                    <div class="col-half">
                                        <div class="input-box">
                                            <label for="add-speed" class="add-label"> Maximum speed (km/h)</label>
                                            <input id="add-speed" type="text" name="speed" class="input-text"
                                                   pattern="[0-9]+([\.,][0-9]+)?" maxlength="20">
                                        </div>
                                    </div>
                                    <div class="col-half">
                                        <div class="input-box">
                                            <label for="add-distance" class="add-label"> Flying distance(km)</label>
                                            <input id="add-distance" type="text" name="dist" class="input-text"
                                                   pattern="[0-9]+([\.,][0-9]+)?" maxlength="20">
                                        </div>
                                    </div>
                                    <div class="col-full">
                                        <div class="input-box">
                                            <label for="add-hashtags" class="add-label"> Hashtags(?, ?, ? ...)</label>
                                            <input id="add-hashtags" type="text" name="hashtags" class="input-text"
                                                   maxlength="100">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-full add-btn-box">
                        <button id="addButton" type="submit"  class="add-btn">
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