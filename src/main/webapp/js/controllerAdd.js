
let dropbox = document.getElementById("dropbox");
let image = document.getElementById("add_img");
let box = document.querySelector(".drag-and-drop");
let fileName;

function onFileLoad(event) {
    fileName = dropbox.files.item(0).name;
    console.log(fileName);
    image.setAttribute("src","images/planes/"+fileName);
    box.style.borderStyle = "none";
}
dropbox.addEventListener('change',onFileLoad);
//
//
//
//
//
//
