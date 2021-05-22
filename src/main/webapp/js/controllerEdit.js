if(registeredUsersArray.getRegisteredUser() == null){
    console.log("You are not registered user. Sign in to enter this page!!!");
    window.location.href="index.jsp";
}

let editForm = document.forms.edit;
let submitBtn = document.querySelector(".edit-btn");
let editingPostID = JSON.parse(localStorage.getItem("editPostID"));

let dropbox = document.getElementById("dropbox");
let image = document.getElementById("add_img");
let box = document.querySelector(".drag-and-drop");


if(editingPostID == null){
    window.location.href="index.jsp";
}
let post = postArray.get(editingPostID);

let fileName = post.photoLink;
image.setAttribute("src",fileName);


pasteValuesToForm(editForm,post);

submitBtn.addEventListener('click', handleEditSubmit);
function handleEditSubmit(event) {
    for (let i = 0; i < editForm.length; i++) {
        if (!editForm[i].checkValidity()) {
            return false;
        }
    }

    let newPost = {};
    newPost.model = editForm.model.value;
    post.height = editForm.height.value === "" ? " - " : editForm.height.value;
    post.type = editForm.type.value;
    post.length = editForm.lengthInput.value === "" ? " - " : editForm.lengthInput.value;
    post.wingspan = editForm.wingspan.value === "" ? " - " : editForm.wingspan.value;
    post.crew = editForm.crew.value === "0" ? " - " : editForm.crew.value;
    post.price = editForm.price.value === "" ? " - " : editForm.price.value;
    post.origin = editForm.origin.value === "" ? " - " : editForm.origin.value;
    post.speed = editForm.speed.value === "" ? " - " : editForm.speed.value;
    post.dist = editForm.dist.value === "" ? " - " : editForm.dist.value;

    post.hashtags = editForm.hashtags.value==="" ? [] : editForm.hashtags.value.split(' ');


    post.photoLink = fileName;

    postArray.edit(editingPostID,newPost);
    localStorage.setItem("editPostID", JSON.stringify(null));

    alert("Post was edited successfully");
    window.location.href="index.jsp";
    return false;
}

function onFileLoad(event) {
    fileName = dropbox.files.item(0).name;
    fileName = "images/planes/" + fileName;
    image.setAttribute("src",fileName);
    box.style.borderStyle = "none";
}
dropbox.addEventListener('change',onFileLoad);




