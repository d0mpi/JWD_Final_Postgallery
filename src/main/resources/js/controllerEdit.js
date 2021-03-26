let editForm = document.forms.edit;
let submitBtn = document.querySelector(".edit-btn");
let editingPostID = JSON.parse(localStorage.getItem("editPostID"));
if(editingPostID == null){
    window.location.href="index.html";
}
let post = postArray.get(editingPostID);

pasteValuesToForm(editForm,post);

submitBtn.addEventListener('click', handleEditSubmit);
function handleEditSubmit(event) {
    for (let i = 1; i < editForm.length; i++) {
        if (!editForm[i].checkValidity()) {
            return false;
        }
    }

    let newPost = {};
    newPost.model = editForm.model.value;
    newPost.height = editForm.height.value === "" ? " - " : editForm.height.value;
    newPost.type = editForm.type.value;
    newPost.length = editForm.lengthInput.value === "" ? " - " : editForm.lengthInput.value;
    newPost.wingspan = editForm.wingspan.value === "" ? " - " : editForm.wingspan.value;
    newPost.crew = editForm.crew.value === "0" ? " - " : editForm.crew.value;
    newPost.price = editForm.price.value === "" ? " - " : editForm.price.value;
    newPost.origin = editForm.origin.value === "" ? " - " : editForm.origin.value;
    newPost.speed = editForm.speed.value === "" ? " - " : editForm.speed.value;
    newPost.dist = editForm.dist.value === "" ? " - " : editForm.dist.value;
    newPost.hashtags = editForm.hashtags.value === "" ? "" : editForm.hashtags.value;

    newPost.photoLink = "";

    postArray.edit(editingPostID,newPost);
    localStorage.setItem("editPostID", JSON.stringify(null));

    alert("Post was edited successfully");
    window.location.href="index.html";
    return false;
}



