<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${cookie['language'].value}" scope="session"/>
<fmt:setBundle basename="text"/>
<fmt:setBundle basename="countries" var="countries"/>
<fmt:setBundle basename="plane_types" var="plane_types"/>
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
                        <div class="edit-label"><fmt:message key="editImage"/>:
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
                                            <label for="edit-height" class="edit-label"> <fmt:message key="postHeight"/>(<fmt:message key="MPostfix"/>)</label>
                                            <input id="edit-height" name="height" type="text" class="input-text"
                                                   pattern="[0-9]{1,10}([\.,][0-9]{1,2})?"
                                                   value="${requestScope.postToEdit.height}">
                                        </div>
                                    </div>
                                    <div class="col-33">
                                        <div class="input-box">
                                            <label for="edit-length" class="edit-label"> <fmt:message key="postLength"/>(<fmt:message key="MPostfix"/>)</label>
                                            <input id="edit-length" type="text" name="lengthInput" class="input-text"
                                                   pattern="[0-9]{1,10}([\.,][0-9]{1,2})?"
                                                   value="${requestScope.postToEdit.length}">
                                        </div>
                                    </div>
                                    <div class="col-33">
                                        <div class="input-box">
                                            <label for="edit-wingspan" class="edit-label"> <fmt:message
                                                    key="postWingspan"/>(<fmt:message key="MPostfix"/>)</label>
                                            <input id="edit-wingspan" type="text" name="wingspan" class="input-text"
                                                   pattern="[0-9]{1,10}([\.,][0-9]{1,2})?"
                                                   value="${requestScope.postToEdit.wingspan}">
                                        </div>
                                    </div>
                                    <div class="col-half">
                                        <div class="input-box">
                                            <label for="edit-type" class="edit-label"> <fmt:message
                                                    key="postType"/></label>
                                            <select id="edit-type" name="type" class="input-text">
                                                <option value="default" label="Select plane type ..."></option>
                                                <option value="fighter"><fmt:message key="fighter" bundle="${plane_types}"/></option>
                                                <option value="transport"><fmt:message key="transport" bundle="${plane_types}"/></option>
                                                <option value="maritime"><fmt:message key="maritime" bundle="${plane_types}"/></option>
                                                <option value="bomber"><fmt:message key="bomber" bundle="${plane_types}"/></option>
                                                <option value="attack"><fmt:message key="attack" bundle="${plane_types}"/></option>
                                                <option value="reconnaissance"><fmt:message key="reconnaissance" bundle="${plane_types}"/></option>
                                                <option value="drone"><fmt:message key="drone" bundle="${plane_types}"/></option>
                                                <option value="airborne"><fmt:message key="airborne" bundle="${plane_types}"/></option>
                                                <option value="experimental"><fmt:message key="experimental" bundle="${plane_types}"/></option>
                                                <option value="electronic"><fmt:message key="electronic" bundle="${plane_types}"/></option>
                                            </select>
                                            <script>
                                                let type = '${requestScope.postToEdit.type}';
                                                const planeTypesArray = document.querySelector('#edit-type').getElementsByTagName('option');
                                                for (let i = 0; i < planeTypesArray.length; i++) {
                                                    if (planeTypesArray[i].value === type) planeTypesArray[i].selected = true;
                                                }
                                            </script>
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
                                                   pattern="[0-9]{1,20}([\.,][0-9]{1,2})*"
                                                   value="${requestScope.postToEdit.price}">
                                        </div>
                                    </div>
                                    <div class="col-half">
                                        <div class="input-box">
                                            <label for="edit-origin" class="edit-label"> <fmt:message
                                                    key="postOrigin"/></label>
                                            <select name="origin" class="input-text" id="edit-origin">
                                                <option value="default" label="Select a country ... ">Select a country ... </option>
                                                <option value="AF" label="<fmt:message key="AF" bundle="${countries}"/>"></option>
                                                <option value="AL" label="<fmt:message key="AL" bundle="${countries}"/>"></option>
                                                <option value="DZ" label="<fmt:message key="DZ" bundle="${countries}"/>"></option>
                                                <option value="AS" label="<fmt:message key="AS" bundle="${countries}"/>"></option>
                                                <option value="AD" label="<fmt:message key="AD" bundle="${countries}"/>"></option>
                                                <option value="AO" label="<fmt:message key="AO" bundle="${countries}"/>"></option>
                                                <option value="AI" label="<fmt:message key="AI" bundle="${countries}"/>"></option>
                                                <option value="AQ" label="<fmt:message key="AQ" bundle="${countries}"/>"></option>
                                                <option value="AG" label="<fmt:message key="AG" bundle="${countries}"/>"></option>
                                                <option value="AR" label="<fmt:message key="AR" bundle="${countries}"/>"></option>
                                                <option value="AM" label="<fmt:message key="AM" bundle="${countries}"/>"></option>
                                                <option value="AW" label="<fmt:message key="AW" bundle="${countries}"/>"></option>
                                                <option value="AU" label="<fmt:message key="AU" bundle="${countries}"/>"></option>
                                                <option value="AT" label="<fmt:message key="AT" bundle="${countries}"/>"></option>
                                                <option value="AZ" label="<fmt:message key="AZ" bundle="${countries}"/>"></option>
                                                <option value="BS" label="<fmt:message key="BS" bundle="${countries}"/>"></option>
                                                <option value="BH" label="<fmt:message key="BH" bundle="${countries}"/>"></option>
                                                <option value="BD" label="<fmt:message key="BD" bundle="${countries}"/>"></option>
                                                <option value="BB" label="<fmt:message key="BB" bundle="${countries}"/>"></option>
                                                <option value="BY" label="<fmt:message key="BY" bundle="${countries}"/>"></option>
                                                <option value="BE" label="<fmt:message key="BE" bundle="${countries}"/>"></option>
                                                <option value="BZ" label="<fmt:message key="BZ" bundle="${countries}"/>"></option>
                                                <option value="BJ" label="<fmt:message key="BJ" bundle="${countries}"/>"></option>
                                                <option value="BM" label="<fmt:message key="BM" bundle="${countries}"/>"></option>
                                                <option value="BT" label="<fmt:message key="BT" bundle="${countries}"/>"></option>
                                                <option value="BO" label="<fmt:message key="BO" bundle="${countries}"/>"></option>
                                                <option value="BA" label="<fmt:message key="BA" bundle="${countries}"/>"></option>
                                                <option value="BW" label="<fmt:message key="BW" bundle="${countries}"/>"></option>
                                                <option value="BV" label="<fmt:message key="BV" bundle="${countries}"/>"></option>
                                                <option value="BR" label="<fmt:message key="BR" bundle="${countries}"/>"></option>
                                                <option value="BQ" label="<fmt:message key="BQ" bundle="${countries}"/>"></option>
                                                <option value="IO" label="<fmt:message key="IO" bundle="${countries}"/>"></option>
                                                <option value="VG" label="<fmt:message key="VG" bundle="${countries}"/>"></option>
                                                <option value="BN" label="<fmt:message key="BN" bundle="${countries}"/>"></option>
                                                <option value="BG" label="<fmt:message key="BG" bundle="${countries}"/>"></option>
                                                <option value="BF" label="<fmt:message key="BF" bundle="${countries}"/>"></option>
                                                <option value="BI" label="<fmt:message key="BI" bundle="${countries}"/>"></option>
                                                <option value="KH" label="<fmt:message key="KH" bundle="${countries}"/>"></option>
                                                <option value="CM" label="<fmt:message key="CM" bundle="${countries}"/>"></option>
                                                <option value="CA" label="<fmt:message key="CA" bundle="${countries}"/>"></option>
                                                <option value="CT" label="<fmt:message key="CT" bundle="${countries}"/>"></option>
                                                <option value="CV" label="<fmt:message key="CV" bundle="${countries}"/>"></option>
                                                <option value="KY" label="<fmt:message key="KY" bundle="${countries}"/>"></option>
                                                <option value="CF" label="<fmt:message key="CF" bundle="${countries}"/>"></option>
                                                <option value="TD" label="<fmt:message key="TD" bundle="${countries}"/>"></option>
                                                <option value="CL" label="<fmt:message key="CL" bundle="${countries}"/>"></option>
                                                <option value="CN" label="<fmt:message key="CN" bundle="${countries}"/>"></option>
                                                <option value="CX" label="<fmt:message key="CX" bundle="${countries}"/>"></option>
                                                <option value="CC" label="<fmt:message key="CC" bundle="${countries}"/>"></option>
                                                <option value="CO" label="<fmt:message key="CO" bundle="${countries}"/>"></option>
                                                <option value="KM" label="<fmt:message key="KM" bundle="${countries}"/>"></option>
                                                <option value="CG" label="<fmt:message key="CG" bundle="${countries}"/>"></option>
                                                <option value="CD" label="<fmt:message key="CD" bundle="${countries}"/>"></option>
                                                <option value="CK" label="<fmt:message key="CK" bundle="${countries}"/>"></option>
                                                <option value="CR" label="<fmt:message key="CR" bundle="${countries}"/>"></option>
                                                <option value="HR" label="<fmt:message key="HR" bundle="${countries}"/>"></option>
                                                <option value="CU" label="<fmt:message key="CU" bundle="${countries}"/>"></option>
                                                <option value="CY" label="<fmt:message key="CY" bundle="${countries}"/>"></option>
                                                <option value="CZ" label="<fmt:message key="CZ" bundle="${countries}"/>"></option>
                                                <option value="CI" label="<fmt:message key="CI" bundle="${countries}"/>"></option>
                                                <option value="DK" label="<fmt:message key="DK" bundle="${countries}"/>"></option>
                                                <option value="DJ" label="<fmt:message key="DJ" bundle="${countries}"/>"></option>
                                                <option value="DM" label="<fmt:message key="DM" bundle="${countries}"/>"></option>
                                                <option value="DO" label="<fmt:message key="DO" bundle="${countries}"/>"></option>
                                                <option value="NQ" label="<fmt:message key="NQ" bundle="${countries}"/>"></option>
                                                <option value="DD" label="<fmt:message key="DD" bundle="${countries}"/>"></option>
                                                <option value="EC" label="<fmt:message key="EC" bundle="${countries}"/>"></option>
                                                <option value="EG" label="<fmt:message key="EG" bundle="${countries}"/>"></option>
                                                <option value="SV" label="<fmt:message key="SV" bundle="${countries}"/>"></option>
                                                <option value="GQ" label="<fmt:message key="GQ" bundle="${countries}"/>"></option>
                                                <option value="ER" label="<fmt:message key="ER" bundle="${countries}"/>"></option>
                                                <option value="EE" label="<fmt:message key="EE" bundle="${countries}"/>"></option>
                                                <option value="ET" label="<fmt:message key="ET" bundle="${countries}"/>"></option>
                                                <option value="FK" label="<fmt:message key="FK" bundle="${countries}"/>"></option>
                                                <option value="FO" label="<fmt:message key="FO" bundle="${countries}"/>"></option>
                                                <option value="FJ" label="<fmt:message key="FJ" bundle="${countries}"/>"></option>
                                                <option value="FI" label="<fmt:message key="FI" bundle="${countries}"/>"></option>
                                                <option value="FR" label="<fmt:message key="FR" bundle="${countries}"/>"></option>
                                                <option value="GF" label="<fmt:message key="GF" bundle="${countries}"/>"></option>
                                                <option value="PF" label="<fmt:message key="PF" bundle="${countries}"/>"></option>
                                                <option value="TF" label="<fmt:message key="TF" bundle="${countries}"/>"></option>
                                                <option value="FQ" label="<fmt:message key="FQ" bundle="${countries}"/>"></option>
                                                <option value="GA" label="<fmt:message key="GA" bundle="${countries}"/>"></option>
                                                <option value="GM" label="<fmt:message key="GM" bundle="${countries}"/>"></option>
                                                <option value="GE" label="<fmt:message key="GE" bundle="${countries}"/>"></option>
                                                <option value="DE" label="<fmt:message key="DE" bundle="${countries}"/>"></option>
                                                <option value="GH" label="<fmt:message key="GH" bundle="${countries}"/>"></option>
                                                <option value="GI" label="<fmt:message key="GI" bundle="${countries}"/>"></option>
                                                <option value="GR" label="<fmt:message key="GR" bundle="${countries}"/>"></option>
                                                <option value="GL" label="<fmt:message key="GL" bundle="${countries}"/>"></option>
                                                <option value="GD" label="<fmt:message key="GD" bundle="${countries}"/>"></option>
                                                <option value="GP" label="<fmt:message key="GP" bundle="${countries}"/>"></option>
                                                <option value="GU" label="<fmt:message key="GU" bundle="${countries}"/>"></option>
                                                <option value="GT" label="<fmt:message key="GT" bundle="${countries}"/>"></option>
                                                <option value="GG" label="<fmt:message key="GG" bundle="${countries}"/>"></option>
                                                <option value="GN" label="<fmt:message key="GN" bundle="${countries}"/>"></option>
                                                <option value="GW" label="<fmt:message key="GW" bundle="${countries}"/>"></option>
                                                <option value="GY" label="<fmt:message key="GY" bundle="${countries}"/>"></option>
                                                <option value="HT" label="<fmt:message key="HT" bundle="${countries}"/>"></option>
                                                <option value="HM" label="<fmt:message key="HM" bundle="${countries}"/>"></option>
                                                <option value="HN" label="<fmt:message key="HN" bundle="${countries}"/>"></option>
                                                <option value="HK" label="<fmt:message key="HK" bundle="${countries}"/>"></option>
                                                <option value="HU" label="<fmt:message key="HU" bundle="${countries}"/>"></option>
                                                <option value="IS" label="<fmt:message key="IS" bundle="${countries}"/>"></option>
                                                <option value="IN" label="<fmt:message key="IN" bundle="${countries}"/>"></option>
                                                <option value="ID" label="<fmt:message key="ID" bundle="${countries}"/>"></option>
                                                <option value="IR" label="<fmt:message key="IR" bundle="${countries}"/>"></option>
                                                <option value="IQ" label="<fmt:message key="IQ" bundle="${countries}"/>"></option>
                                                <option value="IE" label="<fmt:message key="IE" bundle="${countries}"/>"></option>
                                                <option value="IM" label="<fmt:message key="IM" bundle="${countries}"/>"></option>
                                                <option value="IL" label="<fmt:message key="IL" bundle="${countries}"/>"></option>
                                                <option value="IT" label="<fmt:message key="IT" bundle="${countries}"/>"></option>
                                                <option value="JM" label="<fmt:message key="JM" bundle="${countries}"/>"></option>
                                                <option value="JP" label="<fmt:message key="JP" bundle="${countries}"/>"></option>
                                                <option value="JE" label="<fmt:message key="JE" bundle="${countries}"/>"></option>
                                                <option value="JT" label="<fmt:message key="JT" bundle="${countries}"/>"></option>
                                                <option value="JO" label="<fmt:message key="JO" bundle="${countries}"/>"></option>
                                                <option value="KZ" label="<fmt:message key="KZ" bundle="${countries}"/>"></option>
                                                <option value="KE" label="<fmt:message key="KE" bundle="${countries}"/>"></option>
                                                <option value="KI" label="<fmt:message key="KI" bundle="${countries}"/>"></option>
                                                <option value="KW" label="<fmt:message key="KW" bundle="${countries}"/>"></option>
                                                <option value="KG" label="<fmt:message key="KG" bundle="${countries}"/>"></option>
                                                <option value="LA" label="<fmt:message key="LA" bundle="${countries}"/>"></option>
                                                <option value="LV" label="<fmt:message key="LV" bundle="${countries}"/>"></option>
                                                <option value="LB" label="<fmt:message key="LB" bundle="${countries}"/>"></option>
                                                <option value="LS" label="<fmt:message key="LS" bundle="${countries}"/>"></option>
                                                <option value="LR" label="<fmt:message key="LR" bundle="${countries}"/>"></option>
                                                <option value="LY" label="<fmt:message key="LY" bundle="${countries}"/>"></option>
                                                <option value="LI" label="<fmt:message key="LI" bundle="${countries}"/>"></option>
                                                <option value="LT" label="<fmt:message key="LT" bundle="${countries}"/>"></option>
                                                <option value="LU" label="<fmt:message key="LU" bundle="${countries}"/>"></option>
                                                <option value="MO" label="<fmt:message key="MO" bundle="${countries}"/>"></option>
                                                <option value="MK" label="<fmt:message key="MK" bundle="${countries}"/>"></option>
                                                <option value="MG" label="<fmt:message key="MG" bundle="${countries}"/>"></option>
                                                <option value="MW" label="<fmt:message key="MW" bundle="${countries}"/>"></option>
                                                <option value="MY" label="<fmt:message key="MY" bundle="${countries}"/>"></option>
                                                <option value="MV" label="<fmt:message key="MV" bundle="${countries}"/>"></option>
                                                <option value="ML" label="<fmt:message key="ML" bundle="${countries}"/>"></option>
                                                <option value="MT" label="<fmt:message key="MT" bundle="${countries}"/>"></option>
                                                <option value="MH" label="<fmt:message key="MH" bundle="${countries}"/>"></option>
                                                <option value="MQ" label="<fmt:message key="MQ" bundle="${countries}"/>"></option>
                                                <option value="MR" label="<fmt:message key="MR" bundle="${countries}"/>"></option>
                                                <option value="MU" label="<fmt:message key="MU" bundle="${countries}"/>"></option>
                                                <option value="YT" label="<fmt:message key="YT" bundle="${countries}"/>"></option>
                                                <option value="FX" label="<fmt:message key="FX" bundle="${countries}"/>"></option>
                                                <option value="MX" label="<fmt:message key="MX" bundle="${countries}"/>"></option>
                                                <option value="FM" label="<fmt:message key="FM" bundle="${countries}"/>"></option>
                                                <option value="MI" label="<fmt:message key="MI" bundle="${countries}"/>"></option>
                                                <option value="MD" label="<fmt:message key="MD" bundle="${countries}"/>"></option>
                                                <option value="MC" label="<fmt:message key="MC" bundle="${countries}"/>"></option>
                                                <option value="MN" label="<fmt:message key="MN" bundle="${countries}"/>"></option>
                                                <option value="ME" label="<fmt:message key="ME" bundle="${countries}"/>"></option>
                                                <option value="MS" label="<fmt:message key="MS" bundle="${countries}"/>"></option>
                                                <option value="MA" label="<fmt:message key="MA" bundle="${countries}"/>"></option>
                                                <option value="MZ" label="<fmt:message key="MZ" bundle="${countries}"/>"></option>
                                                <option value="MM" label="<fmt:message key="MM" bundle="${countries}"/>"></option>
                                                <option value="NA" label="<fmt:message key="NA" bundle="${countries}"/>"></option>
                                                <option value="NR" label="<fmt:message key="NR" bundle="${countries}"/>"></option>
                                                <option value="NP" label="<fmt:message key="NP" bundle="${countries}"/>"></option>
                                                <option value="NL" label="<fmt:message key="NL" bundle="${countries}"/>"></option>
                                                <option value="AN" label="<fmt:message key="AN" bundle="${countries}"/>"></option>
                                                <option value="NT" label="<fmt:message key="NT" bundle="${countries}"/>"></option>
                                                <option value="NC" label="<fmt:message key="NC" bundle="${countries}"/>"></option>
                                                <option value="NZ" label="<fmt:message key="NZ" bundle="${countries}"/>"></option>
                                                <option value="NI" label="<fmt:message key="NI" bundle="${countries}"/>"></option>
                                                <option value="NE" label="<fmt:message key="NE" bundle="${countries}"/>"></option>
                                                <option value="NG" label="<fmt:message key="NG" bundle="${countries}"/>"></option>
                                                <option value="NU" label="<fmt:message key="NU" bundle="${countries}"/>"></option>
                                                <option value="NF" label="<fmt:message key="NF" bundle="${countries}"/>"></option>
                                                <option value="KP" label="<fmt:message key="KP" bundle="${countries}"/>"></option>
                                                <option value="VD" label="<fmt:message key="VD" bundle="${countries}"/>"></option>
                                                <option value="MP" label="<fmt:message key="MP" bundle="${countries}"/>"></option>
                                                <option value="NO" label="<fmt:message key="NO" bundle="${countries}"/>"></option>
                                                <option value="OM" label="<fmt:message key="OM" bundle="${countries}"/>"></option>
                                                <option value="PC" label="<fmt:message key="PC" bundle="${countries}"/>"></option>
                                                <option value="PK" label="<fmt:message key="PK" bundle="${countries}"/>"></option>
                                                <option value="PW" label="<fmt:message key="PW" bundle="${countries}"/>"></option>
                                                <option value="PS" label="<fmt:message key="PS" bundle="${countries}"/>"></option>
                                                <option value="PA" label="<fmt:message key="PA" bundle="${countries}"/>"></option>
                                                <option value="PZ" label="<fmt:message key="PZ" bundle="${countries}"/>"></option>
                                                <option value="PG" label="<fmt:message key="PG" bundle="${countries}"/>"></option>
                                                <option value="PY" label="<fmt:message key="PY" bundle="${countries}"/>"></option>
                                                <option value="YD" label="<fmt:message key="YD" bundle="${countries}"/>"></option>
                                                <option value="PE" label="<fmt:message key="PE" bundle="${countries}"/>"></option>
                                                <option value="PH" label="<fmt:message key="PH" bundle="${countries}"/>"></option>
                                                <option value="PN" label="<fmt:message key="PN" bundle="${countries}"/>"></option>
                                                <option value="PL" label="<fmt:message key="PL" bundle="${countries}"/>"></option>
                                                <option value="PT" label="<fmt:message key="PT" bundle="${countries}"/>"></option>
                                                <option value="PR" label="<fmt:message key="PR" bundle="${countries}"/>"></option>
                                                <option value="QA" label="<fmt:message key="QA" bundle="${countries}"/>"></option>
                                                <option value="RO" label="<fmt:message key="RO" bundle="${countries}"/>"></option>
                                                <option value="RU" label="<fmt:message key="RU" bundle="${countries}"/>"></option>
                                                <option value="RW" label="<fmt:message key="RW" bundle="${countries}"/>"></option>
                                                <option value="RE" label="<fmt:message key="RE" bundle="${countries}"/>"></option>
                                                <option value="BL" label="<fmt:message key="BL" bundle="${countries}"/>"></option>
                                                <option value="SH" label="<fmt:message key="SH" bundle="${countries}"/>"></option>
                                                <option value="KN" label="<fmt:message key="KN" bundle="${countries}"/>"></option>
                                                <option value="LC" label="<fmt:message key="LC" bundle="${countries}"/>"></option>
                                                <option value="MF" label="<fmt:message key="MF" bundle="${countries}"/>"></option>
                                                <option value="PM" label="<fmt:message key="PM" bundle="${countries}"/>"></option>
                                                <option value="VC" label="<fmt:message key="VC" bundle="${countries}"/>"></option>
                                                <option value="WS" label="<fmt:message key="WS" bundle="${countries}"/>"></option>
                                                <option value="SM" label="<fmt:message key="SM" bundle="${countries}"/>"></option>
                                                <option value="SA" label="<fmt:message key="SA" bundle="${countries}"/>"></option>
                                                <option value="SN" label="<fmt:message key="SN" bundle="${countries}"/>"></option>
                                                <option value="RS" label="<fmt:message key="RS" bundle="${countries}"/>"></option>
                                                <option value="CS" label="<fmt:message key="CS" bundle="${countries}"/>"></option>
                                                <option value="SC" label="<fmt:message key="SC" bundle="${countries}"/>"></option>
                                                <option value="SL" label="<fmt:message key="SL" bundle="${countries}"/>"></option>
                                                <option value="SG" label="<fmt:message key="SG" bundle="${countries}"/>"></option>
                                                <option value="SK" label="<fmt:message key="SK" bundle="${countries}"/>"></option>
                                                <option value="SI" label="<fmt:message key="SI" bundle="${countries}"/>"></option>
                                                <option value="SB" label="<fmt:message key="SB" bundle="${countries}"/>"></option>
                                                <option value="SO" label="<fmt:message key="SO" bundle="${countries}"/>"></option>
                                                <option value="ZA" label="<fmt:message key="ZA" bundle="${countries}"/>"></option>
                                                <option value="GS" label="<fmt:message key="GS" bundle="${countries}"/>"></option>
                                                <option value="KR" label="<fmt:message key="KR" bundle="${countries}"/>"></option>
                                                <option value="ES" label="<fmt:message key="ES" bundle="${countries}"/>"></option>
                                                <option value="LK" label="<fmt:message key="LK" bundle="${countries}"/>"></option>
                                                <option value="SD" label="<fmt:message key="SD" bundle="${countries}"/>"></option>
                                                <option value="SR" label="<fmt:message key="SR" bundle="${countries}"/>"></option>
                                                <option value="SJ" label="<fmt:message key="SJ" bundle="${countries}"/>"></option>
                                                <option value="SZ" label="<fmt:message key="SZ" bundle="${countries}"/>"></option>
                                                <option value="SE" label="<fmt:message key="SE" bundle="${countries}"/>"></option>
                                                <option value="CH" label="<fmt:message key="CH" bundle="${countries}"/>"></option>
                                                <option value="SY" label="<fmt:message key="SY" bundle="${countries}"/>"></option>
                                                <option value="ST" label="<fmt:message key="ST" bundle="${countries}"/>"></option>
                                                <option value="TW" label="<fmt:message key="TW" bundle="${countries}"/>"></option>
                                                <option value="TJ" label="<fmt:message key="TJ" bundle="${countries}"/>"></option>
                                                <option value="TZ" label="<fmt:message key="TZ" bundle="${countries}"/>"></option>
                                                <option value="TH" label="<fmt:message key="TH" bundle="${countries}"/>"></option>
                                                <option value="TL" label="<fmt:message key="TL" bundle="${countries}"/>"></option>
                                                <option value="TG" label="<fmt:message key="TG" bundle="${countries}"/>"></option>
                                                <option value="TK" label="<fmt:message key="TK" bundle="${countries}"/>"></option>
                                                <option value="TO" label="<fmt:message key="TO" bundle="${countries}"/>"></option>
                                                <option value="TT" label="<fmt:message key="TT" bundle="${countries}"/>"></option>
                                                <option value="TN" label="<fmt:message key="TN" bundle="${countries}"/>"></option>
                                                <option value="TR" label="<fmt:message key="TR" bundle="${countries}"/>"></option>
                                                <option value="TM" label="<fmt:message key="TM" bundle="${countries}"/>"></option>
                                                <option value="TC" label="<fmt:message key="TC" bundle="${countries}"/>"></option>
                                                <option value="TV" label="<fmt:message key="TV" bundle="${countries}"/>"></option>
                                                <option value="UM" label="<fmt:message key="UM" bundle="${countries}"/>"></option>
                                                <option value="PU" label="<fmt:message key="PU" bundle="${countries}"/>"></option>
                                                <option value="VI" label="<fmt:message key="VI" bundle="${countries}"/>"></option>
                                                <option value="UG" label="<fmt:message key="UG" bundle="${countries}"/>"></option>
                                                <option value="UA" label="<fmt:message key="UA" bundle="${countries}"/>"></option>
                                                <option value="SU" label="<fmt:message key="SU" bundle="${countries}"/>"></option>
                                                <option value="AE" label="<fmt:message key="AE" bundle="${countries}"/>"></option>
                                                <option value="GB" label="<fmt:message key="GB" bundle="${countries}"/>"></option>
                                                <option value="US" label="<fmt:message key="US" bundle="${countries}"/>"></option>
                                                <option value="ZZ" label="<fmt:message key="ZZ" bundle="${countries}"/>"></option>
                                                <option value="UY" label="<fmt:message key="UY" bundle="${countries}"/>"></option>
                                                <option value="UZ" label="<fmt:message key="UZ" bundle="${countries}"/>"></option>
                                                <option value="VU" label="<fmt:message key="VU" bundle="${countries}"/>"></option>
                                                <option value="VA" label="<fmt:message key="VA" bundle="${countries}"/>"></option>
                                                <option value="VE" label="<fmt:message key="VE" bundle="${countries}"/>"></option>
                                                <option value="VN" label="<fmt:message key="VN" bundle="${countries}"/>"></option>
                                                <option value="WK" label="<fmt:message key="WK" bundle="${countries}"/>"></option>
                                                <option value="WF" label="<fmt:message key="WF" bundle="${countries}"/>"></option>
                                                <option value="EH" label="<fmt:message key="EH" bundle="${countries}"/>"></option>
                                                <option value="YE" label="<fmt:message key="YE" bundle="${countries}"/>"></option>
                                                <option value="ZM" label="<fmt:message key="ZM" bundle="${countries}"/>"></option>
                                                <option value="ZW" label="<fmt:message key="ZW" bundle="${countries}"/>"></option>
                                                <option value="AX" label="<fmt:message key="AX" bundle="${countries}"/>"></option>
                                                <script>
                                                    let country = '${requestScope.postToEdit.origin}';
                                                    const optionArr = document.querySelector('#edit-origin').getElementsByTagName('option');
                                                    for (let i = 0; i < optionArr.length; i++) {
                                                        if (optionArr[i].value === country) optionArr[i].selected = true;
                                                    }
                                                </script>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-half">
                                        <div class="input-box">
                                            <label for="edit-speed" class="edit-label"> <fmt:message
                                                    key="postMaxSpeed"/> (<fmt:message key="KmHPostfix"/>)</label>
                                            <input id="edit-speed" type="text" name="speed" class="input-text"
                                                   pattern="[0-9]{1,10}([\.,][0-9]{1,2})?" maxlength="20"
                                                   value="${requestScope.postToEdit.speed}">
                                        </div>
                                    </div>
                                    <div class="col-half">
                                        <div class="input-box">
                                            <label for="edit-distance" class="edit-label"> <fmt:message
                                                    key="postFlyingDist"/>(<fmt:message key="KmPostfix"/>)</label>
                                            <input id="edit-distance" type="text" name="dist" class="input-text"
                                                   pattern="[0-9]{1,10}([\.,][0-9]{1,2})?" maxlength="20"
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
                        <input id="editButton" type="submit" class="edit-btn" value="<fmt:message
                                                    key="editBtn"/>"/>
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