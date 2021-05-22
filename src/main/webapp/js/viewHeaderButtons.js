let inButton = document.getElementById("sign_in_btn");
let outButton = document.getElementById("sign_out_btn");
let addButton = document.getElementById("add_btn");
let usernameHeader = document.getElementById("header_username");

function hideButton(button){
    button.style.display = "none";
}

function showButton(button){
    button.style.display = "flex";
}

function handleInButton(){
    if(!registeredUsersArray.isUserRegistered()){
        showButton(inButton);
        hideButton(outButton);
        hideButton(addButton);
    } else {
        hideButton(inButton);
        showButton(outButton);
        showButton(addButton);
        usernameHeader.textContent = registeredUsersArray.getRegisteredUser().username;
        showButton(usernameHeader);
    }
}
handleInButton();

function onClickOutButton(event){
    registeredUsersArray.signOut();
    location.reload();
}
outButton.addEventListener('click', onClickOutButton);


