<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${cookie['language'].value}" scope="session"/>
<fmt:setBundle basename = "text" var = "text"/>
<fmt:setBundle basename = "countries" var = "countries"/>
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
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
                        <h3><fmt:message key="addLabel" bundle="${text}"/></h3>
                    </div>
                    <div class="col-half" style="height: 504px">
                        <div class="img-box col-full">
                            <div class="add-label"><fmt:message key="editImage" bundle="${text}"/></div>
                            <div id="drag-and-drop">
                                <img src="" alt="" class="add-img" id="add_img">
                                <i class="fas fa-download fa-8x" id="download-icon"></i>
                                <div class="drag-text">
                                    <label class="drop-button" id="image-label">Choose a file
                                        <input type="file" name="file" id="file" class="input-file"
                                               accept="image/jpeg,image/png,image/jpg">
                                    </label>
                                    <p id="image-text">&#160or drag it here</p>
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
                                                <fmt:message key="postModel" bundle="${text}"/>*</label>
                                            <input id="add-model" name="model" type="text" class="input-text" required
                                                   maxlength="100">
                                        </div>
                                    </div>
                                    <div class="col-33">
                                        <div class="input-box">
                                            <label for="add-height" class="add-label"> <fmt:message key="postHeight" bundle="${text}"/>(<fmt:message key="MPostfix" bundle="${text}"/>)</label>
                                            <input id="add-height" name="height" type="text" class="input-text"
                                                   pattern="[0-9]{1,10}([\.,][0-9]{1,2})?">
                                        </div>
                                    </div>
                                    <div class="col-33">
                                        <div class="input-box">
                                            <label for="add-length" class="add-label"> <fmt:message key="postLength" bundle="${text}"/>(<fmt:message key="MPostfix" bundle="${text}"/>)</label>
                                            <input id="add-length" type="text" name="lengthInput" class="input-text"
                                                   pattern="[0-9]{1,10}([\.,][0-9]{1,2})?">
                                        </div>
                                    </div>
                                    <div class="col-33">
                                        <div class="input-box">
                                            <label for="add-wingspan" class="add-label"> <fmt:message
                                                    key="postWingspan"/>(<fmt:message key="MPostfix" bundle="${text}"/>)</label>
                                            <input id="add-wingspan" type="text" name="wingspan" class="input-text"
                                                   pattern="[0-9]{1,10}([\.,][0-9]{1,2})?">
                                        </div>
                                    </div>
                                    <div class="col-half">
                                        <div class="input-box">
                                            <label for="add-type" class="add-label"> <fmt:message
                                                    key="postType"/></label>
                                            <select id="add-type" name="type" type="text" class="input-text">
                                                <option><fmt:message key="planeTypeFighter" bundle="${text}"/></option>
                                                <option><fmt:message key="planeTypeTransport" bundle="${text}"/></option>
                                                <option><fmt:message key="planeTypeMaritime" bundle="${text}"/></option>
                                                <option><fmt:message key="planeTypeBomber" bundle="${text}"/></option>
                                                <option><fmt:message key="planeTypeAttack" bundle="${text}"/></option>
                                                <option><fmt:message key="planeTypeReconnaissance" bundle="${text}"/></option>
                                                <option><fmt:message key="planeTypeMultirole" bundle="${text}"/></option>
                                                <option><fmt:message key="planeTypeAirborne" bundle="${text}"/></option>
                                                <option><fmt:message key="planeTypeExperimental" bundle="${text}"/></option>
                                                <option><fmt:message key="planeTypeElectronic" bundle="${text}"/></option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-half">
                                        <div class="input-box">
                                            <label for="add-crew" class="add-label"> <fmt:message
                                                    key="postCrew" bundle="${text}"/></label>
                                            <input id="add-crew" type="number" name="crew" class="input-text" min="0"
                                                   max="100" value="0">
                                        </div>
                                    </div>
                                    <div class="col-half">
                                        <div class="input-box">
                                            <label for="price" class="add-label" title="This field is mandatory">
                                                <fmt:message key="postPrice" bundle="${text}"/>($)</label>
                                            <input id="price" type="text" name="price" class="input-text" required
                                                   pattern="[0-9]{1,20}([\.,][0-9]{1,2})*">
                                        </div>
                                    </div>
                                    <div class="col-half">
                                        <div class="input-box">
                                            <label for="add-origin" class="add-label"> <fmt:message key="postOrigin" bundle="${text}"/></label>
                                            <select name="origin" class="input-text" id="add-origin">
                                                <option value="" label="Select a country ... " selected="selected">Select a country ... </option>
                                                <option value="AF" label="<fmt:message key="AF" bundle="${countries}"/>">Afghanistan</option>
                                                <option value="AL" label="<fmt:message key="AL" bundle="${countries}"/>">Albania</option>
                                                <option value="DZ" label="<fmt:message key="DZ" bundle="${countries}"/>">Algeria</option>
                                                <option value="AS" label="<fmt:message key="AS" bundle="${countries}"/>">American Samoa</option>
                                                <option value="AD" label="<fmt:message key="AD" bundle="${countries}"/>">Andorra</option>
                                                <option value="AO" label="<fmt:message key="AO" bundle="${countries}"/>">Angola</option>
                                                <option value="AI" label="<fmt:message key="AI" bundle="${countries}"/>">Anguilla</option>
                                                <option value="AQ" label="<fmt:message key="AQ" bundle="${countries}"/>">Antarctica</option>
                                                <option value="AG" label="<fmt:message key="AG" bundle="${countries}"/>">Antigua and Barbuda</option>
                                                <option value="AR" label="<fmt:message key="AR" bundle="${countries}"/>">Argentina</option>
                                                <option value="AM" label="<fmt:message key="AM" bundle="${countries}"/>">Armenia</option>
                                                <option value="AW" label="<fmt:message key="AW" bundle="${countries}"/>">Aruba</option>
                                                <option value="AU" label="<fmt:message key="AU" bundle="${countries}"/>">Australia</option>
                                                <option value="AT" label="<fmt:message key="AT" bundle="${countries}"/>">Austria</option>
                                                <option value="AZ" label="<fmt:message key="AZ" bundle="${countries}"/>">Azerbaijan</option>
                                                <option value="BS" label="<fmt:message key="BS" bundle="${countries}"/>">Bahamas</option>
                                                <option value="BH" label="<fmt:message key="BH" bundle="${countries}"/>">Bahrain</option>
                                                <option value="BD" label="<fmt:message key="BD" bundle="${countries}"/>">Bangladesh</option>
                                                <option value="BB" label="<fmt:message key="BB" bundle="${countries}"/>">Barbados</option>
                                                <option value="BY" label="<fmt:message key="BY" bundle="${countries}"/>">Belarus</option>
                                                <option value="BE" label="<fmt:message key="BE" bundle="${countries}"/>">Belgium</option>
                                                <option value="BZ" label="<fmt:message key="BZ" bundle="${countries}"/>">Belize</option>
                                                <option value="BJ" label="<fmt:message key="BJ" bundle="${countries}"/>">Benin</option>
                                                <option value="BM" label="<fmt:message key="BM" bundle="${countries}"/>">Bermuda</option>
                                                <option value="BT" label="<fmt:message key="BT" bundle="${countries}"/>">Bhutan</option>
                                                <option value="BO" label="<fmt:message key="BO" bundle="${countries}"/>">Bolivia</option>
                                                <option value="BA" label="<fmt:message key="BA" bundle="${countries}"/>">Bosnia and Herzegovina</option>
                                                <option value="BW" label="<fmt:message key="BW" bundle="${countries}"/>">Botswana</option>
                                                <option value="BV" label="<fmt:message key="BV" bundle="${countries}"/>">Bouvet Island</option>
                                                <option value="BR" label="<fmt:message key="BR" bundle="${countries}"/>">Brazil</option>
                                                <option value="BQ" label="<fmt:message key="BQ" bundle="${countries}"/>">British Antarctic Territory</option>
                                                <option value="IO" label="<fmt:message key="IO" bundle="${countries}"/>">British Indian Ocean Territory</option>
                                                <option value="VG" label="<fmt:message key="VG" bundle="${countries}"/>">British Virgin Islands</option>
                                                <option value="BN" label="<fmt:message key="BN" bundle="${countries}"/>">Brunei</option>
                                                <option value="BG" label="<fmt:message key="BG" bundle="${countries}"/>">Bulgaria</option>
                                                <option value="BF" label="<fmt:message key="BF" bundle="${countries}"/>">Burkina Faso</option>
                                                <option value="BI" label="<fmt:message key="BI" bundle="${countries}"/>">Burundi</option>
                                                <option value="KH" label="<fmt:message key="KH" bundle="${countries}"/>">Cambodia</option>
                                                <option value="CM" label="<fmt:message key="CM" bundle="${countries}"/>">Cameroon</option>
                                                <option value="CA" label="<fmt:message key="CA" bundle="${countries}"/>">Canada</option>
                                                <option value="CT" label="<fmt:message key="CT" bundle="${countries}"/>">Canton and Enderbury Islands</option>
                                                <option value="CV" label="<fmt:message key="CV" bundle="${countries}"/>">Cape Verde</option>
                                                <option value="KY" label="<fmt:message key="KY" bundle="${countries}"/>">Cayman Islands</option>
                                                <option value="CF" label="<fmt:message key="CF" bundle="${countries}"/>">Central African Republic</option>
                                                <option value="TD" label="<fmt:message key="TD" bundle="${countries}"/>">Chad</option>
                                                <option value="CL" label="<fmt:message key="CL" bundle="${countries}"/>">Chile</option>
                                                <option value="CN" label="<fmt:message key="CN" bundle="${countries}"/>">China</option>
                                                <option value="CX" label="<fmt:message key="CX" bundle="${countries}"/>">Christmas Island</option>
                                                <option value="CC" label="<fmt:message key="CC" bundle="${countries}"/>">Cocos [Keeling] Islands</option>
                                                <option value="CO" label="<fmt:message key="CO" bundle="${countries}"/>">Colombia</option>
                                                <option value="KM" label="<fmt:message key="KM" bundle="${countries}"/>">Comoros</option>
                                                <option value="CG" label="<fmt:message key="CG" bundle="${countries}"/>">Congo - Brazzaville</option>
                                                <option value="CD" label="<fmt:message key="CD" bundle="${countries}"/>">Congo - Kinshasa</option>
                                                <option value="CK" label="<fmt:message key="CK" bundle="${countries}"/>">Cook Islands</option>
                                                <option value="CR" label="<fmt:message key="CR" bundle="${countries}"/>">Costa Rica</option>
                                                <option value="HR" label="<fmt:message key="HR" bundle="${countries}"/>">Croatia</option>
                                                <option value="CU" label="<fmt:message key="CU" bundle="${countries}"/>">Cuba</option>
                                                <option value="CY" label="<fmt:message key="CY" bundle="${countries}"/>">Cyprus</option>
                                                <option value="CZ" label="<fmt:message key="CZ" bundle="${countries}"/>">Czech Republic</option>
                                                <option value="CI" label="<fmt:message key="CI" bundle="${countries}"/>">Côte d’Ivoire</option>
                                                <option value="DK" label="<fmt:message key="DK" bundle="${countries}"/>">Denmark</option>
                                                <option value="DJ" label="<fmt:message key="DJ" bundle="${countries}"/>">Djibouti</option>
                                                <option value="DM" label="<fmt:message key="DM" bundle="${countries}"/>">Dominica</option>
                                                <option value="DO" label="<fmt:message key="DO" bundle="${countries}"/>">Dominican Republic</option>
                                                <option value="NQ" label="<fmt:message key="NQ" bundle="${countries}"/>">Dronning Maud Land</option>
                                                <option value="DD" label="<fmt:message key="DD" bundle="${countries}"/>">East Germany</option>
                                                <option value="EC" label="<fmt:message key="EC" bundle="${countries}"/>">Ecuador</option>
                                                <option value="EG" label="<fmt:message key="EG" bundle="${countries}"/>">Egypt</option>
                                                <option value="SV" label="<fmt:message key="SV" bundle="${countries}"/>">El Salvador</option>
                                                <option value="GQ" label="<fmt:message key="GQ" bundle="${countries}"/>">Equatorial Guinea</option>
                                                <option value="ER" label="<fmt:message key="ER" bundle="${countries}"/>">Eritrea</option>
                                                <option value="EE" label="<fmt:message key="EE" bundle="${countries}"/>">Estonia</option>
                                                <option value="ET" label="<fmt:message key="ET" bundle="${countries}"/>">Ethiopia</option>
                                                <option value="FK" label="<fmt:message key="FK" bundle="${countries}"/>">Falkland Islands</option>
                                                <option value="FO" label="<fmt:message key="FO" bundle="${countries}"/>">Faroe Islands</option>
                                                <option value="FJ" label="<fmt:message key="FJ" bundle="${countries}"/>">Fiji</option>
                                                <option value="FI" label="<fmt:message key="FI" bundle="${countries}"/>">Finland</option>
                                                <option value="FR" label="<fmt:message key="FR" bundle="${countries}"/>">France</option>
                                                <option value="GF" label="<fmt:message key="GF" bundle="${countries}"/>">French Guiana</option>
                                                <option value="PF" label="<fmt:message key="PF" bundle="${countries}"/>">French Polynesia</option>
                                                <option value="TF" label="<fmt:message key="TF" bundle="${countries}"/>">French Southern Territories</option>
                                                <option value="FQ" label="<fmt:message key="FQ" bundle="${countries}"/>">French Southern and Antarctic Territories</option>
                                                <option value="GA" label="<fmt:message key="GA" bundle="${countries}"/>">Gabon</option>
                                                <option value="GM" label="<fmt:message key="GM" bundle="${countries}"/>">Gambia</option>
                                                <option value="GE" label="<fmt:message key="GE" bundle="${countries}"/>">Georgia</option>
                                                <option value="DE" label="<fmt:message key="DE" bundle="${countries}"/>">Germany</option>
                                                <option value="GH" label="<fmt:message key="GH" bundle="${countries}"/>">Ghana</option>
                                                <option value="GI" label="<fmt:message key="GI" bundle="${countries}"/>">Gibraltar</option>
                                                <option value="GR" label="<fmt:message key="GR" bundle="${countries}"/>">Greece</option>
                                                <option value="GL" label="<fmt:message key="GL" bundle="${countries}"/>">Greenland</option>
                                                <option value="GD" label="<fmt:message key="GD" bundle="${countries}"/>">Grenada</option>
                                                <option value="GP" label="<fmt:message key="GP" bundle="${countries}"/>">Guadeloupe</option>
                                                <option value="GU" label="<fmt:message key="GU" bundle="${countries}"/>">Guam</option>
                                                <option value="GT" label="<fmt:message key="GT" bundle="${countries}"/>">Guatemala</option>
                                                <option value="GG" label="<fmt:message key="GG" bundle="${countries}"/>">Guernsey</option>
                                                <option value="GN" label="<fmt:message key="GN" bundle="${countries}"/>">Guinea</option>
                                                <option value="GW" label="<fmt:message key="GW" bundle="${countries}"/>">Guinea-Bissau</option>
                                                <option value="GY" label="<fmt:message key="GY" bundle="${countries}"/>">Guyana</option>
                                                <option value="HT" label="<fmt:message key="HT" bundle="${countries}"/>">Haiti</option>
                                                <option value="HM" label="<fmt:message key="HM" bundle="${countries}"/>">Heard Island and McDonald Islands</option>
                                                <option value="HN" label="<fmt:message key="HN" bundle="${countries}"/>">Honduras</option>
                                                <option value="HK" label="<fmt:message key="HK" bundle="${countries}"/>">Hong Kong SAR China</option>
                                                <option value="HU" label="<fmt:message key="HU" bundle="${countries}"/>">Hungary</option>
                                                <option value="IS" label="<fmt:message key="IS" bundle="${countries}"/>">Iceland</option>
                                                <option value="IN" label="<fmt:message key="IN" bundle="${countries}"/>">India</option>
                                                <option value="ID" label="<fmt:message key="ID" bundle="${countries}"/>">Indonesia</option>
                                                <option value="IR" label="<fmt:message key="IR" bundle="${countries}"/>">Iran</option>
                                                <option value="IQ" label="<fmt:message key="IQ" bundle="${countries}"/>">Iraq</option>
                                                <option value="IE" label="<fmt:message key="IE" bundle="${countries}"/>">Ireland</option>
                                                <option value="IM" label="<fmt:message key="IM" bundle="${countries}"/>">Isle of Man</option>
                                                <option value="IL" label="<fmt:message key="IL" bundle="${countries}"/>">Israel</option>
                                                <option value="IT" label="<fmt:message key="IT" bundle="${countries}"/>">Italy</option>
                                                <option value="JM" label="<fmt:message key="JM" bundle="${countries}"/>">Jamaica</option>
                                                <option value="JP" label="<fmt:message key="JP" bundle="${countries}"/>">Japan</option>
                                                <option value="JE" label="<fmt:message key="JE" bundle="${countries}"/>">Jersey</option>
                                                <option value="JT" label="<fmt:message key="JT" bundle="${countries}"/>">Johnston Island</option>
                                                <option value="JO" label="<fmt:message key="JO" bundle="${countries}"/>">Jordan</option>
                                                <option value="KZ" label="<fmt:message key="KZ" bundle="${countries}"/>">Kazakhstan</option>
                                                <option value="KE" label="<fmt:message key="KE" bundle="${countries}"/>">Kenya</option>
                                                <option value="KI" label="<fmt:message key="KI" bundle="${countries}"/>">Kiribati</option>
                                                <option value="KW" label="<fmt:message key="KW" bundle="${countries}"/>">Kuwait</option>
                                                <option value="KG" label="<fmt:message key="KG" bundle="${countries}"/>">Kyrgyzstan</option>
                                                <option value="LA" label="<fmt:message key="LA" bundle="${countries}"/>">Laos</option>
                                                <option value="LV" label="<fmt:message key="LV" bundle="${countries}"/>">Latvia</option>
                                                <option value="LB" label="<fmt:message key="LB" bundle="${countries}"/>">Lebanon</option>
                                                <option value="LS" label="<fmt:message key="LS" bundle="${countries}"/>">Lesotho</option>
                                                <option value="LR" label="<fmt:message key="LR" bundle="${countries}"/>">Liberia</option>
                                                <option value="LY" label="<fmt:message key="LY" bundle="${countries}"/>">Libya</option>
                                                <option value="LI" label="<fmt:message key="LI" bundle="${countries}"/>">Liechtenstein</option>
                                                <option value="LT" label="<fmt:message key="LT" bundle="${countries}"/>">Lithuania</option>
                                                <option value="LU" label="<fmt:message key="LU" bundle="${countries}"/>">Luxembourg</option>
                                                <option value="MO" label="<fmt:message key="MO" bundle="${countries}"/>">Macau SAR China</option>
                                                <option value="MK" label="<fmt:message key="MK" bundle="${countries}"/>">Macedonia</option>
                                                <option value="MG" label="<fmt:message key="MG" bundle="${countries}"/>">Madagascar</option>
                                                <option value="MW" label="<fmt:message key="MW" bundle="${countries}"/>">Malawi</option>
                                                <option value="MY" label="<fmt:message key="MY" bundle="${countries}"/>">Malaysia</option>
                                                <option value="MV" label="<fmt:message key="MV" bundle="${countries}"/>">Maldives</option>
                                                <option value="ML" label="<fmt:message key="ML" bundle="${countries}"/>">Mali</option>
                                                <option value="MT" label="<fmt:message key="MT" bundle="${countries}"/>">Malta</option>
                                                <option value="MH" label="<fmt:message key="MH" bundle="${countries}"/>">Marshall Islands</option>
                                                <option value="MQ" label="<fmt:message key="MQ" bundle="${countries}"/>">Martinique</option>
                                                <option value="MR" label="<fmt:message key="MR" bundle="${countries}"/>">Mauritania</option>
                                                <option value="MU" label="<fmt:message key="MU" bundle="${countries}"/>">Mauritius</option>
                                                <option value="YT" label="<fmt:message key="YT" bundle="${countries}"/>">Mayotte</option>
                                                <option value="FX" label="<fmt:message key="FX" bundle="${countries}"/>">Metropolitan France</option>
                                                <option value="MX" label="<fmt:message key="MX" bundle="${countries}"/>">Mexico</option>
                                                <option value="FM" label="<fmt:message key="FM" bundle="${countries}"/>">Micronesia</option>
                                                <option value="MI" label="<fmt:message key="MI" bundle="${countries}"/>">Midway Islands</option>
                                                <option value="MD" label="<fmt:message key="MD" bundle="${countries}"/>">Moldova</option>
                                                <option value="MC" label="<fmt:message key="MC" bundle="${countries}"/>">Monaco</option>
                                                <option value="MN" label="<fmt:message key="MN" bundle="${countries}"/>">Mongolia</option>
                                                <option value="ME" label="<fmt:message key="ME" bundle="${countries}"/>">Montenegro</option>
                                                <option value="MS" label="<fmt:message key="MS" bundle="${countries}"/>">Montserrat</option>
                                                <option value="MA" label="<fmt:message key="MA" bundle="${countries}"/>">Morocco</option>
                                                <option value="MZ" label="<fmt:message key="MZ" bundle="${countries}"/>">Mozambique</option>
                                                <option value="MM" label="<fmt:message key="MM" bundle="${countries}"/>">Myanmar [Burma]</option>
                                                <option value="NA" label="<fmt:message key="NA" bundle="${countries}"/>">Namibia</option>
                                                <option value="NR" label="<fmt:message key="NR" bundle="${countries}"/>">Nauru</option>
                                                <option value="NP" label="<fmt:message key="NP" bundle="${countries}"/>">Nepal</option>
                                                <option value="NL" label="<fmt:message key="NL" bundle="${countries}"/>">Netherlands</option>
                                                <option value="AN" label="<fmt:message key="AN" bundle="${countries}"/>">Netherlands Antilles</option>
                                                <option value="NT" label="<fmt:message key="NT" bundle="${countries}"/>">Neutral Zone</option>
                                                <option value="NC" label="<fmt:message key="NC" bundle="${countries}"/>">New Caledonia</option>
                                                <option value="NZ" label="<fmt:message key="NZ" bundle="${countries}"/>">New Zealand</option>
                                                <option value="NI" label="<fmt:message key="NI" bundle="${countries}"/>">Nicaragua</option>
                                                <option value="NE" label="<fmt:message key="NE" bundle="${countries}"/>">Niger</option>
                                                <option value="NG" label="<fmt:message key="NG" bundle="${countries}"/>">Nigeria</option>
                                                <option value="NU" label="<fmt:message key="NU" bundle="${countries}"/>">Niue</option>
                                                <option value="NF" label="<fmt:message key="NF" bundle="${countries}"/>">Norfolk Island</option>
                                                <option value="KP" label="<fmt:message key="KP" bundle="${countries}"/>">North Korea</option>
                                                <option value="VD" label="<fmt:message key="VD" bundle="${countries}"/>">North Vietnam</option>
                                                <option value="MP" label="<fmt:message key="MP" bundle="${countries}"/>">Northern Mariana Islands</option>
                                                <option value="NO" label="<fmt:message key="NO" bundle="${countries}"/>">Norway</option>
                                                <option value="OM" label="<fmt:message key="OM" bundle="${countries}"/>">Oman</option>
                                                <option value="PC" label="<fmt:message key="PC" bundle="${countries}"/>">Pacific Islands Trust Territory</option>
                                                <option value="PK" label="<fmt:message key="PK" bundle="${countries}"/>">Pakistan</option>
                                                <option value="PW" label="<fmt:message key="PW" bundle="${countries}"/>">Palau</option>
                                                <option value="PS" label="<fmt:message key="PS" bundle="${countries}"/>">Palestinian Territories</option>
                                                <option value="PA" label="<fmt:message key="PA" bundle="${countries}"/>">Panama</option>
                                                <option value="PZ" label="<fmt:message key="PZ" bundle="${countries}"/>">Panama Canal Zone</option>
                                                <option value="PG" label="<fmt:message key="PG" bundle="${countries}"/>">Papua New Guinea</option>
                                                <option value="PY" label="<fmt:message key="PY" bundle="${countries}"/>">Paraguay</option>
                                                <option value="YD" label="<fmt:message key="YD" bundle="${countries}"/>">People's Democratic Republic of Yemen</option>
                                                <option value="PE" label="<fmt:message key="PE" bundle="${countries}"/>">Peru</option>
                                                <option value="PH" label="<fmt:message key="PH" bundle="${countries}"/>">Philippines</option>
                                                <option value="PN" label="<fmt:message key="PN" bundle="${countries}"/>">Pitcairn Islands</option>
                                                <option value="PL" label="<fmt:message key="PL" bundle="${countries}"/>">Poland</option>
                                                <option value="PT" label="<fmt:message key="PT" bundle="${countries}"/>">Portugal</option>
                                                <option value="PR" label="<fmt:message key="PR" bundle="${countries}"/>">Puerto Rico</option>
                                                <option value="QA" label="<fmt:message key="QA" bundle="${countries}"/>">Qatar</option>
                                                <option value="RO" label="<fmt:message key="RO" bundle="${countries}"/>">Romania</option>
                                                <option value="RU" label="<fmt:message key="RU" bundle="${countries}"/>">Russia</option>
                                                <option value="RW" label="<fmt:message key="RW" bundle="${countries}"/>">Rwanda</option>
                                                <option value="RE" label="<fmt:message key="RE" bundle="${countries}"/>">Réunion</option>
                                                <option value="BL" label="<fmt:message key="BL" bundle="${countries}"/>">Saint Barthélemy</option>
                                                <option value="SH" label="<fmt:message key="SH" bundle="${countries}"/>">Saint Helena</option>
                                                <option value="KN" label="<fmt:message key="KN" bundle="${countries}"/>">Saint Kitts and Nevis</option>
                                                <option value="LC" label="<fmt:message key="LC" bundle="${countries}"/>">Saint Lucia</option>
                                                <option value="MF" label="<fmt:message key="MF" bundle="${countries}"/>">Saint Martin</option>
                                                <option value="PM" label="<fmt:message key="PM" bundle="${countries}"/>">Saint Pierre and Miquelon</option>
                                                <option value="VC" label="<fmt:message key="VC" bundle="${countries}"/>">Saint Vincent and the Grenadines</option>
                                                <option value="WS" label="<fmt:message key="WS" bundle="${countries}"/>">Samoa</option>
                                                <option value="SM" label="<fmt:message key="SM" bundle="${countries}"/>">San Marino</option>
                                                <option value="SA" label="<fmt:message key="SA" bundle="${countries}"/>">Saudi Arabia</option>
                                                <option value="SN" label="<fmt:message key="SN" bundle="${countries}"/>">Senegal</option>
                                                <option value="RS" label="<fmt:message key="RS" bundle="${countries}"/>">Serbia</option>
                                                <option value="CS" label="<fmt:message key="CS" bundle="${countries}"/>">Serbia and Montenegro</option>
                                                <option value="SC" label="<fmt:message key="SC" bundle="${countries}"/>">Seychelles</option>
                                                <option value="SL" label="<fmt:message key="SL" bundle="${countries}"/>">Sierra Leone</option>
                                                <option value="SG" label="<fmt:message key="SG" bundle="${countries}"/>">Singapore</option>
                                                <option value="SK" label="<fmt:message key="SK" bundle="${countries}"/>">Slovakia</option>
                                                <option value="SI" label="<fmt:message key="SI" bundle="${countries}"/>">Slovenia</option>
                                                <option value="SB" label="<fmt:message key="SB" bundle="${countries}"/>">Solomon Islands</option>
                                                <option value="SO" label="<fmt:message key="SO" bundle="${countries}"/>">Somalia</option>
                                                <option value="ZA" label="<fmt:message key="ZA" bundle="${countries}"/>">South Africa</option>
                                                <option value="GS" label="<fmt:message key="GS" bundle="${countries}"/>">South Georgia and the South Sandwich Islands</option>
                                                <option value="KR" label="<fmt:message key="KR" bundle="${countries}"/>">South Korea</option>
                                                <option value="ES" label="<fmt:message key="ES" bundle="${countries}"/>">Spain</option>
                                                <option value="LK" label="<fmt:message key="LK" bundle="${countries}"/>">Sri Lanka</option>
                                                <option value="SD" label="<fmt:message key="SD" bundle="${countries}"/>">Sudan</option>
                                                <option value="SR" label="<fmt:message key="SR" bundle="${countries}"/>">Suriname</option>
                                                <option value="SJ" label="<fmt:message key="SJ" bundle="${countries}"/>">Svalbard and Jan Mayen</option>
                                                <option value="SZ" label="<fmt:message key="SZ" bundle="${countries}"/>">Swaziland</option>
                                                <option value="SE" label="<fmt:message key="SE" bundle="${countries}"/>">Sweden</option>
                                                <option value="CH" label="<fmt:message key="CH" bundle="${countries}"/>">Switzerland</option>
                                                <option value="SY" label="<fmt:message key="SY" bundle="${countries}"/>">Syria</option>
                                                <option value="ST" label="<fmt:message key="ST" bundle="${countries}"/>">São Tomé and Príncipe</option>
                                                <option value="TW" label="<fmt:message key="TW" bundle="${countries}"/>">Taiwan</option>
                                                <option value="TJ" label="<fmt:message key="TJ" bundle="${countries}"/>">Tajikistan</option>
                                                <option value="TZ" label="<fmt:message key="TZ" bundle="${countries}"/>">Tanzania</option>
                                                <option value="TH" label="<fmt:message key="TH" bundle="${countries}"/>">Thailand</option>
                                                <option value="TL" label="<fmt:message key="TL" bundle="${countries}"/>">Timor-Leste</option>
                                                <option value="TG" label="<fmt:message key="TG" bundle="${countries}"/>">Togo</option>
                                                <option value="TK" label="<fmt:message key="TK" bundle="${countries}"/>">Tokelau</option>
                                                <option value="TO" label="<fmt:message key="TO" bundle="${countries}"/>">Tonga</option>
                                                <option value="TT" label="<fmt:message key="TT" bundle="${countries}"/>">Trinidad and Tobago</option>
                                                <option value="TN" label="<fmt:message key="TN" bundle="${countries}"/>">Tunisia</option>
                                                <option value="TR" label="<fmt:message key="TR" bundle="${countries}"/>">Turkey</option>
                                                <option value="TM" label="<fmt:message key="TM" bundle="${countries}"/>">Turkmenistan</option>
                                                <option value="TC" label="<fmt:message key="TC" bundle="${countries}"/>">Turks and Caicos Islands</option>
                                                <option value="TV" label="<fmt:message key="TV" bundle="${countries}"/>">Tuvalu</option>
                                                <option value="UM" label="<fmt:message key="UM" bundle="${countries}"/>">U.S. Minor Outlying Islands</option>
                                                <option value="PU" label="<fmt:message key="PU" bundle="${countries}"/>">U.S. Miscellaneous Pacific Islands</option>
                                                <option value="VI" label="<fmt:message key="VI" bundle="${countries}"/>">U.S. Virgin Islands</option>
                                                <option value="UG" label="<fmt:message key="UG" bundle="${countries}"/>">Uganda</option>
                                                <option value="UA" label="<fmt:message key="UA" bundle="${countries}"/>">Ukraine</option>
                                                <option value="SU" label="<fmt:message key="SU" bundle="${countries}"/>">Union of Soviet Socialist Republics</option>
                                                <option value="AE" label="<fmt:message key="AE" bundle="${countries}"/>">United Arab Emirates</option>
                                                <option value="GB" label="<fmt:message key="GB" bundle="${countries}"/>">United Kingdom</option>
                                                <option value="US" label="<fmt:message key="US" bundle="${countries}"/>">United States</option>
                                                <option value="ZZ" label="<fmt:message key="ZZ" bundle="${countries}"/>">Unknown or Invalid Region</option>
                                                <option value="UY" label="<fmt:message key="UY" bundle="${countries}"/>">Uruguay</option>
                                                <option value="UZ" label="<fmt:message key="UZ" bundle="${countries}"/>">Uzbekistan</option>
                                                <option value="VU" label="<fmt:message key="VU" bundle="${countries}"/>">Vanuatu</option>
                                                <option value="VA" label="<fmt:message key="VA" bundle="${countries}"/>">Vatican City</option>
                                                <option value="VE" label="<fmt:message key="VE" bundle="${countries}"/>">Venezuela</option>
                                                <option value="VN" label="<fmt:message key="VN" bundle="${countries}"/>">Vietnam</option>
                                                <option value="WK" label="<fmt:message key="WK" bundle="${countries}"/>">Wake Island</option>
                                                <option value="WF" label="<fmt:message key="WF" bundle="${countries}"/>">Wallis and Futuna</option>
                                                <option value="EH" label="<fmt:message key="EH" bundle="${countries}"/>">Western Sahara</option>
                                                <option value="YE" label="<fmt:message key="YE" bundle="${countries}"/>">Yemen</option>
                                                <option value="ZM" label="<fmt:message key="ZM" bundle="${countries}"/>">Zambia</option>
                                                <option value="ZW" label="<fmt:message key="ZW" bundle="${countries}"/>">Zimbabwe</option>
                                                <option value="AX" label="<fmt:message key="AX" bundle="${countries}"/>">Åland Islands</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-half">
                                        <div class="input-box">
                                            <label for="add-speed" class="add-label"> <fmt:message key="postMaxSpeed" bundle="${text}"/>
                                                (<fmt:message key="KmHPostfix" bundle="${text}"/>)</label>
                                            <input id="add-speed" type="text" name="speed" class="input-text"
                                                   pattern="[0-9]{1,10}([\.,][0-9]{1,2})?" maxlength="20">
                                        </div>
                                    </div>
                                    <div class="col-half">
                                        <div class="input-box">
                                            <label for="add-distance" class="add-label"> <fmt:message
                                                    key="postFlyingDist" bundle="${text}"/>(<fmt:message key="KmPostfix" bundle="${text}"/>)</label>
                                            <input id="add-distance" type="text" name="dist" class="input-text"
                                                   pattern="[0-9]{1,10}([\.,][0-9]{1,2})?" maxlength="20">
                                        </div>
                                    </div>
                                    <div class="col-full">
                                        <div class="input-box">
                                            <label for="add-hashtags" class="add-label"> <fmt:message
                                                    key="editHashtags" bundle="${text}"/></label>
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
                            <fmt:message key="createBtn" bundle="${text}"/>
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