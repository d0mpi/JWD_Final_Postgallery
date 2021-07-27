<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${cookie['language'].value}" scope="session"/>
<fmt:setBundle basename="text"/>
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>About us</title>
    <link rel="stylesheet" type="text/css" href="../../css/index-styles.css">
    <link rel="stylesheet" type="text/css" href="../../css/about-styles.css">
    <script src="https://kit.fontawesome.com/ede64561b8.js" crossorigin="anonymous"></script>
    <link rel="stylesheet" type="text/css" href="../../fonts/fonts.css">
    <jsp:include page="title-logo.jsp"/>
</head>
<body>
<jsp:include page="header.jsp"/>
<main>
    <div class="main-container">
        <div class="main-col">
            <div class="part">
                <div class="col-full title-left-text"><fmt:message key="aboutWelcomeTitle"/></div>
                <div class="col-half img-box">
                    <div class=" col-full">
                        <img src="../../images/logo-blue.png" alt="" class="title-logo">
                    </div>
                </div>
                <div class="title-right-text col-half">
                    <fmt:message key="aboutWelcomeText"/>
                </div>
            </div>
            <div class="part back-blur">
                <div class="col-half">
                    <div class="col-full contact-title"><fmt:message key="aboutJoinTitle"/></div>
                    <div class="sign-text col-full">
                        <fmt:message key="aboutJoinText"/>
                    </div>
                    <div class="col-full add-post-img">
                        <img alt="" src="../../images/add-post.png">
                    </div>
                </div>
                <div class="sign-img col-half">
                    <img alt="" src="../../images/register.png">
                </div>
            </div>
            <div class="part">
                <div class="size-images-block">
                    <div class="size-title col-half">
                        <fmt:message key="aboutResponsiveTitle"/>
                    </div>
                    <div class="size-image col-half">
                        <img src="../../images/size-1.png" alt="">
                    </div>
                </div>
                <div class="size-text">
                    <fmt:message key="aboutResponsiveText"/>
                </div>
            </div>

            <div class="part back-blur">
                <div class="feature-block col-33">
                    <div class="circle-info col-full">
                        <i class="fas fa-edit"></i>
                    </div>
                    <h3><fmt:message key="aboutEditTitle"/></h3>
                    <div class="col-full feature-left-text">
                        <fmt:message key="aboutEditText"/>
                    </div>
                </div>
                <div class="feature-block-center col-33">
                    <div class="col-full feature-center-text">
                        <fmt:message key="aboutDeleteText"/>
                    </div>
                    <h3><fmt:message key="aboutDeleteTitle"/></h3>
                    <div class="circle-info col-full">
                        <i class="fas fa-delete fa-trash-alt"></i>
                    </div>
                </div>
                <div class="feature-block col-33">
                    <div class="circle-info col-full">
                        <i class="fas fa-like fa-heart"></i>
                    </div>

                    <h3><fmt:message key="aboutLikeTitle"/></h3>
                    <div class="col-full feature-right-text">
                        <fmt:message key="aboutLikeText"/>
                    </div>
                </div>
            </div>

            <div class="part">
                <div class="col-half post-text">
                    <div class="col-full post-title"><fmt:message key="aboutStructureTitle1"/> <br><fmt:message
                            key="aboutStructureTitle2"/></div>
                    <div class="col-full size-text">
                        <fmt:message key="aboutStructureText"/>
                    </div>
                </div>
                <div class="post-img col-half">
                    <img alt="" src="../../images/post.png">
                </div>
            </div>
            <div class="part back-blur">
                <div class="contact-img col-half">
                    <img alt="" src="../../images/contact-us.png">
                </div>
                <div class="col-half">
                    <div class="contact-title col-full">
                        <fmt:message key="aboutContactTitle"/>
                    </div>
                    <div class="contact-text col-full">
                        <fmt:message key="aboutContactText"/>
                    </div>
                </div>
            </div>


            <div class="part">
                <div class="col-full filter-title"><fmt:message key="filterTitle"/></div>
                <hr class="col-full">
                <div class="filter-block col-33">
                    <div class="circle-filter col-full">
                        <div class="fa-user far fa-user"></div>
                    </div>
                    <h3><fmt:message key="aboutAuthorTitle"/></h3>
                    <div class="col-full filter-text">
                        <fmt:message key="aboutAuthorText"/>
                    </div>

                </div>

                <div class="filter-block-center col-33">
                    <div class="col-full filter-text">
                        <fmt:message key="aboutDateText"/>
                    </div>
                    <h3><fmt:message key="aboutDateTitle"/></h3>
                    <div class="circle-filter col-full">
                        <div class="fa-date fas fa-calendar-alt"></div>
                    </div>
                </div>
                <div class="filter-block col-33">
                    <div class="circle-filter col-full">
                        <div class="fa-hash fas fa-hashtag"></div>
                    </div>

                    <h3><fmt:message key="aboutHashtagTitle"/></h3>
                    <div class="col-full filter-text">
                        <fmt:message key="aboutHashtagText"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>

<jsp:include page="footer.jsp"/>
</body>
</html>