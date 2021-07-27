let fileInput = document.getElementById("file");
let image = document.getElementById("add_img");
let dropbox = document.getElementById("drag-and-drop");
let text = document.querySelector(".image-text");

dropbox.addEventListener("dragenter", dragenter, false);
dropbox.addEventListener("dragover", dragover, false);
dropbox.addEventListener("dragleave", dragleave, false);
dropbox.addEventListener("drop", drop, false);


function dragenter(e) {
    e.stopPropagation();
    e.preventDefault();
}

function dragover(e) {
    e.stopPropagation();
    e.preventDefault();
}

function drop(e) {
    e.stopPropagation();
    e.preventDefault();
    let dt = e.dataTransfer;
    let files = dt.files;
    fileInput.files = dt.files;

    handleFiles(files);
}

function dragleave(e) {
    return false;
}

function onFileLoad(event) {
    handleFiles(fileInput.files);
}

fileInput.addEventListener('change', onFileLoad);

function handleFiles(files) {
    for (let i = 0; i < files.length; i++) {
        const file = files[i];
        if (!file.type.startsWith('image/')) {
            continue
        }
        image.file = file;
        const reader = new FileReader();
        reader.onload = (function (aImg) {
            return function (e) {
                aImg.src = e.target.result;
            };
        })(image);
        reader.readAsDataURL(file);
    }
}

