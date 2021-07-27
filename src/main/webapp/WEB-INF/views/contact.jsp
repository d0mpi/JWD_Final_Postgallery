<%@ page  contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${cookie['language'].value}" scope="session"/>
<fmt:setBundle basename="text"/>
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
    <meta name="viewport" content="width=device-width, initial-scale=1 text/html; charset=cp1251">
    <title>Contact</title>
    <link rel="stylesheet" type="text/css" href="../../css/index-styles.css">
    <link rel="stylesheet" type="text/css" href="../../css/contact-styles.css">
    <script src="https://kit.fontawesome.com/ede64561b8.js" crossorigin="anonymous"></script>
    <link rel="stylesheet" type="text/css" href="../../fonts/fonts.css">
    <jsp:include page="title-logo.jsp"/>
</head>
<body>
<jsp:include page="header.jsp"/>
<main>
    <div class="main-col">
        <div class="col-50-1">
            <div class="contact-stripped-box">
                <div class="contact-main-box">
                    <div class="contact-title col-full">
                        <h3><fmt:message key="aboutContactTitle"/></h3>
                    </div>
                    <form accept-charset="UTF-8" class="contact-form" method="post"
                          action="${pageContext.request.contextPath}/controller?command=send_mail">
                        <div class="row">
                            <div class="col-half mb-20">
                                <div class="contact-input">
                                    <label for="contact-name" class="contact-label"> <fmt:message
                                            key="name"/>*</label>
                                    <input id="contact-name" name="contact-name" type="text" class="input-name"
                                           required>
                                </div>
                            </div>
                            <div class="col-half mb-20">
                                <div class="contact-input">
                                    <label for="contact-email" class="contact-label"> <fmt:message
                                            key="email"/>*</label>
                                    <input id="contact-email" name="contact-email" type="email" class="input-email"
                                           required>
                                </div>
                            </div>
                            <div class="col-full mb-20">
                                <div class="contact-input">
                                    <label for="contact-text" class="contact-label"> <fmt:message
                                            key="message"/>*</label>
                                    <textarea id="contact-text" name="contact-text" type="text" class="input-text"
                                              required> </textarea>
                                </div>
                            </div>
                        </div>
                        <div class="col-full contact-btn-box">
                            <input type="submit" value="<fmt:message key="sendBtn"/>" class="contact-btn">
                        </div>
                    </form>
                </div>
            </div>
            <div class="push"></div>
        </div>
    </div>

</main>
<jsp:include page="footer.jsp"/>
</body>
</html>