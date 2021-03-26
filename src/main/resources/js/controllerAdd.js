let addForm = document.forms.add;
let submitBtn = document.querySelector(".add-btn");

submitBtn.addEventListener('click', handleAddSubmit);

function handleAddSubmit(event) {

    for (let i = 1; i < addForm.length; i++) {
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

    post.hashtags = addForm.hashtags.value.split(' ');
    post.likes = [];
    post.author = "admin";
    post.createdAt = new Date();
    post.id = postArray.getLastId()+1;
    post.photoLink = "";

    postArray.add(post);

    alert("Post was created successfully");
}



