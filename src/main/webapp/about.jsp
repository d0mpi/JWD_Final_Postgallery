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
    <title>About us</title>
    <link rel="stylesheet" type="text/css" href="css/index-styles.css">
    <link rel="stylesheet" type="text/css" href="css/about-styles.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="fonts/fonts.css">
    <jsp:include page="WEB-INF/title-logo.jsp"/>
    <script src="js/modelRegistration.js" defer></script>
    <script src="js/viewHeaderButtons.js" defer></script>
</head>
<body>
<jsp:include page="WEB-INF/header.jsp"/>
<main>
    <div class="main-container">
        <div class="main-col">
            <div class="part">
                <div class="col-full title-left-text"> Welcome to</div>
                <div class="col-half img-box">
                    <div class=" col-full">
                        <img src="images/logo-blue.png" alt="" class="title-logo">
                    </div>
                </div>
                <div class="title-right-text col-half">
                    UP! - is a convenient gallery or feed of posts about military aircraft.
                    The web application was developed by Michael Dokuchaev,
                    a BSU student of the 2nd year of the 9th group. Here you can
                    find images and descriptions of military aircraft around the world published by other users.
                </div>
            </div>
            <div class="part back-blur">
                <div class="col-half">
                    <div class="col-full contact-title">Join us</div>
                    <div class="sign-text col-full">
                        Have you looked at all the posts added by other authors or do you want to contribute to
                        the development of the project? Join our community and get the opportunity to add your posts!
                        In the upper right corner of the main page, there is a sign in button, where you can log in
                        or register a new account. After logging in, you will be able to access the button for adding
                        posts in the upper central part of the screen, clicking on it will take you to the page for
                        adding a post, where you need to fill in the required fields. You will also be able to edit
                        and delete your posts. Try it!
                    </div>
                    <div class="col-full add-post-img">
                        <img alt="" src="images/add-post.png">
                    </div>
                </div>
                <div class="sign-img col-half">
                    <img alt="" src="images/register.png">
                </div>


            </div>

            <div class="part">

                <div class="size-images-block">
                    <div class="size-title col-half">
                        Responsive design
                    </div>
                    <div class="size-image col-half">
                        <img src="images/size-1.png" alt="">
                    </div>
                </div>
                <div class="size-text">
                    The web application is optimized for most modern and outdated screen sizes.
                    Try changing the size of your browser window and see how the size of the page elements
                    and their location will change. You can also use the computer version of the web app on mobile
                    devices;
                    the mobile version is coming soon!!!
                </div>
            </div>

            <div class="part back-blur">
                <div class="feature-block col-33">
                    <div class="circle-info col-full">
                        <div class="fa-edit"></div>
                    </div>
                    <h3>Edit</h3>
                    <div class="col-full feature-left-text">
                        Your post bothers you, you don't like the way it looks, or the information specified in the post
                        is no longer relevant? Edit your post and wait for new likes. Only for authorized users!
                    </div>

                </div>

                <div class="feature-block-center col-33">
                    <div class="col-full feature-center-text">
                        You are an authorized user and you have a post that you don't like? You can delete it!
                    </div>
                    <h3>Delete</h3>
                    <div class="circle-info col-full">
                        <div class="fa-delete"></div>
                    </div>
                </div>
                <div class="feature-block col-33">
                    <div class="circle-info col-full">
                        <div class="fa-like"></div>
                    </div>

                    <h3>Like it!</h3>
                    <div class="col-full feature-right-text">
                        Mark the posts that you are interested in. If you are an authorized user, you can put a like in
                        the lower right corner of the post, thereby rating the post.
                    </div>
                </div>
            </div>

            <div class="part">
                <div class="col-half post-text">
                    <div class="col-full post-title">Post <br>structure</div>
                    <div class="col-full size-text">
                        Convenient and attractive structure of the post.<br> In the left part there is an image of the
                        plane.
                        And in the right part you can see all the information about the post.
                        Model, type, length, width, wingspan, country of manufacture, crew, maximum speed, maximum
                        flight
                        distance and price - learn all about the aircraft shown in the picture.<br> Under the transport
                        description is the name of the author, the date of addition and the id of the post.
                        In the lower right corner, you can rate the post. Under the post card is a list of hashtags,
                        and in the right part you can find buttons for editing and deleting your post (only for
                        authorized users).
                    </div>
                </div>
                <div class="post-img col-half">
                    <img alt="" src="images/post.png">
                </div>
            </div>
            <div class="part back-blur">
                <div class="contact-img col-half">
                    <img alt="" src="images/contact-us.png">
                </div>
                <div class="col-half">
                    <div class="contact-title col-full">
                        Contact us!!!
                    </div>
                    <div class="contact-text col-full">
                        Any questions? Do you have any suggestions for further development of the project?
                        Or do you just lack communication?
                        We are always in touch!<br> On the "Contact us" page, you can contact the web-application
                        development team. Fill in all the fields in a convenient and adaptive form and ask a
                        question about the published post or the work of the web application as a whole.
                    </div>
                </div>
            </div>


            <div class="part">
                <div class="col-full filter-title">FILTER</div>
                <hr class="col-full">
                <div class="filter-block col-33">
                    <div class="circle-filter col-full">
                        <div class="fa-user"></div>
                    </div>
                    <h3>Author</h3>
                    <div class="col-full filter-text">
                        Do you like the publications of a certain author? Do you want to see your friend's posts?
                        Easily!!! You can display the posts of a single author in your feed using a filter.
                    </div>

                </div>

                <div class="filter-block-center col-33">
                    <div class="col-full filter-text">
                        You saw an unusual post a few days ago, but you don't remember the post id or the author's name?
                        Do you want to see what posts were published by users on Defender of the Fatherland Day?
                        Filter posts in your feed by the date they were added.
                    </div>
                    <h3>Date</h3>
                    <div class="circle-filter col-full">
                        <div class="fa-date"></div>
                    </div>
                </div>
                <div class="filter-block col-33">
                    <div class="circle-filter col-full">
                        <div class="fa-hash"></div>
                    </div>

                    <h3>Hashtag</h3>
                    <div class="col-full filter-text">
                        Our web application offers a convenient hashtag system.
                        Add a few keywords when adding your post, such as the country of origin of the aircraft
                        or the name of the creator's company, to make it easier for other users to find it.
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>

<jsp:include page="WEB-INF/footer.jsp"/>
</body>
</html>