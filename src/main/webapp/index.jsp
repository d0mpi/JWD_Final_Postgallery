<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Main</title>
    <link rel="stylesheet" type="text/css" href="css/index-styles.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="fonts/fonts.css">

    <script src="js/modelRegistration.js" defer></script>
    <script src="js/viewPost.js" defer></script>
    <script src="js/modelPosts.js" defer></script>

    <script src="js/controllerFeed.js" defer></script>
    <script src="js/viewHeaderButtons.js" defer></script>

</head>
<body>
<header>
    <div class="header-flex-box">
        <div class="header-left">
            <div class="header-logo-col">
                <a class="header-logo-ref" href="index.jsp">
                    <img class="img-logo" src="images/logo-white.png" alt="header-logo">
                </a>
            </div>
            <nav class="header-menu">
                <div class="menu-col ">
                    <a href="index.jsp" class="menu-href menu-href-active">
                        <div></div>
                        HOME</a>
                </div>
                <div class="menu-col">
                    <a href="about.jsp" class="menu-href">
                        <div></div>
                        ABOUT</a>
                </div>
                <div class="menu-col">
                    <a href="contact.jsp" class="menu-href">
                        <div></div>
                        CONTACT US</a>
                </div>
                <div class="menu-col">
                    <a href="error.jsp" class="menu-href">
                        <div></div>
                        ERROR</a>
                </div>
            </nav>
        </div>
        <div class="header-add-col">
            <button onclick="document.location='add-post.jsp'" class="header-add-button hide" id="add_btn">
                ADD POST
            </button>
        </div>

        <div class="header-log-col">
            <div class="header-username-col hide" id="header_username"> UserName</div>

            <button class="header-button" onclick="document.location='sign.jsp'" id="sign_in_btn"> SIGN IN </button>
            <button class="header-button" id="sign_out_btn"> SIGN OUT</button>
        </div>
    </div>
</header>

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
                                <input name="filter_hashtags_checkbox" type="checkbox" class="filter-checkbox" placeholder="Hashtag">
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
                <!-- Посты добавляются сюда -->
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

<footer>
    <ul class="footer-ul font-footer">
        <li class="footer-li-1">
            <img class="img-logo" src="images/logo-white.png" alt="logo">
        </li>
        <li class="footer-li-2">Michael Dokuchaev</li>
        <li class="footer-li-3">2k9g</li>
        <li class="footer-li-4">mdokuchaevp@gmail.com</li>
        <li class="footer-li-5">25.03.2021</li>
    </ul>
</footer>

</body>
</html>