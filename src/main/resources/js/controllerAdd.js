if(registeredUsersArray.getRegisteredUser() == null){
    console.log("You are not registered user. Sign in to enter this page!!!");
    window.location.href="index.html";
}

let addForm = document.forms.add;
let submitBtn = document.querySelector(".add-btn");
let dropbox = document.getElementById("dropbox");
let image = document.getElementById("add_img");
let box = document.querySelector(".drag-and-drop");
let fileName;

submitBtn.addEventListener('click', handleAddSubmit);

function handleAddSubmit(event) {
    for (let i = 0; i < addForm.length; i++) {
        if (!addForm[i].checkValidity()) {
            return;
        }
    }

    let post = {};
    post.model = addForm.model.value;
    post.height = addForm.height.value === "" ? " - " : addForm.height.value;
    post.type = addForm.type.value;
    post.length = addForm.lengthInput.value === "" ? " - " : addForm.lengthInput.value;
    post.wingspan = addForm.wingspan.value === "" ? " - " : addForm.wingspan.value;
    post.crew = addForm.crew.value === "0" ? " - " : addForm.crew.value;
    post.price = addForm.price.value === "" ? " - " : addForm.price.value;
    post.origin = addForm.origin.value === "" ? " - " : addForm.origin.value;
    post.speed = addForm.speed.value === "" ? " - " : addForm.speed.value;
    post.dist = addForm.dist.value === "" ? " - " : addForm.dist.value;
    post.hashtags = addForm.hashtags.value==="" ? [] : addForm.hashtags.value.split(' ');
    post.likes = [];
    post.author = registeredUsersArray.getRegisteredUser().username;
    post.createdAt = new Date();
    post.id = postArray.getLastId()+1;
    post.photoLink = "images/planes/"+fileName;

    postArray.add(post);

    alert("Post was created successfully");
}


function onFileLoad(event) {
    fileName = dropbox.files.item(0).name;
    console.log(fileName);
    image.setAttribute("src","images/planes/"+fileName);
    box.style.borderStyle = "none";
}
dropbox.addEventListener('change',onFileLoad);






