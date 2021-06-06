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
    <title>Contact</title>
    <link rel="stylesheet" type="text/css" href="../../css/index-styles.css">
    <link rel="stylesheet" type="text/css" href="../../css/contact-styles.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="../../fonts/fonts.css">
    <jsp:include page="title-logo.jsp"/>
</head>
<body>
<jsp:include page="header.jsp"/>
<main>
    <div class="main-container">
        <div class="main-col">
            <div class="col-50-1">
                <div class="contact-stripped-box">
                    <div class="contact-main-box">
                        <div class="contact-title col-full">
                            <h3>Get In Touch</h3>
                        </div>
                        <form class="contact-form" method="post">
                            <div class="row">
                                <div class="col-half mb-20">
                                    <div class="contact-input">
                                        <label for="contact-name" class="contact-label"> Name*</label>
                                        <input id="contact-name" type="text" class="input-name" required>
                                    </div>
                                </div>
                                <div class="col-half mb-20">
                                    <div class="contact-input">
                                        <label for="contact-email" class="contact-label"> Email*</label>
                                        <input id="contact-email" type="email" class="input-email" required>
                                    </div>
                                </div>
                                <div class="col-full mb-20">
                                    <div class="contact-input">
                                        <label for="contact-title" class="contact-label"> Subject*</label>
                                        <input id="contact-title" type="text" class="input-title" required>
                                    </div>
                                </div>
                                <div class="col-full mb-20">
                                    <div class="contact-input">
                                        <label for="contact-text" class="contact-label"> Your message*</label>
                                        <textarea id="contact-text" type="text" class="input-text" required> </textarea>
                                    </div>
                                </div>
                            </div>
                        </form>
                        <div class="col-full contact-btn-box">
                            <button type="submit" class="contact-btn">
                                Send
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="push"></div>
        </div>
    </div>

</main>

<jsp:include page="footer.jsp"/>

</body>
</html>