let registrationForm = document.forms.register_form;
let registerButton = document.getElementById("register_btn");

if(registeredUsersArray.getRegisteredUser() !== null){
    console.log("You are registered user. Sign out to enter this page!!!");
    window.location.href="index.jsp";
}

function onClickRegisterButton(){
    for (let i = 0; i < registrationForm.length; i++) {
        if (!registrationForm[i].checkValidity()) {
            return false;
        }
    }

    let newUser = {};

    newUser.id = registeredUsersArray.getLastId() + 1;
    newUser.username = registrationForm.username.value;
    if(!registeredUsersArray.doesUserExist(newUser.username)) {
        alert("This name is taken!! Please enter new username!!!");
        return false;
    }

    newUser.password = registrationForm.password.value;
    newUser.email = registrationForm.email.value;
    if(!registeredUsersArray.doesEmailExist(newUser.email)) {
        alert("This email is registered!! Please enter new email!!!");
        return false;
    }

    registeredUsersArray.add(newUser);
    registeredUsersArray.setRegisteredUser(newUser.id);

    alert("User was registered successfully");
    location.reload();
    return false;
}
registerButton.addEventListener('click',onClickRegisterButton);
