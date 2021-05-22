let signForm = document.forms.sign_form;
let signButton = document.getElementById("sign_btn");

if(registeredUsersArray.getRegisteredUser() != null){
    console.log("You are registered user. Sign out to enter this page!!!");
    window.location.href="index.jsp";
}

function onClickSignButton(){
    for (let i = 0; i < signForm.length; i++) {
        if (!signForm[i].checkValidity()) {
            return false;
        }
    }

    let enteredUser = {};
    let realPassword = "";

    enteredUser.username = signForm.username.value;
    if(registeredUsersArray.doesUserExist(enteredUser.username)) {
        alert("User with this name does not exist");
        return false;
    } else {
        enteredUser.id = registeredUsersArray.getUserIDByUsername(enteredUser.username);
        enteredUser.email = registeredUsersArray.getUserFromArray(enteredUser.id).email;
        realPassword = registeredUsersArray.getUserFromArray(enteredUser.id).password;
        if(String(signForm.password.value) === String(realPassword)) {
            enteredUser.password = signForm.password.value;
        } else {
            alert("Wrong password!!!");
            return false;
        }
    }

    registeredUsersArray.setRegisteredUser(enteredUser.id);

    alert("User was signed successfully");
    location.reload();
    return false;
}
signButton.addEventListener('click',onClickSignButton);


