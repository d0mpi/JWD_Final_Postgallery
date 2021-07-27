let fileInput = document.getElementById("file");
let image = document.getElementById("add_img");
let dropbox = document.getElementById("drag-and-drop");
let text = document.getElementById("image-label");
let icon = document.getElementById("download-icon");
let text2 = document.getElementById("image-text");

dropbox.addEventListener("dragenter", dragenter, false);
dropbox.addEventListener("dragover", dragover, false);
dropbox.addEventListener("dragleave", dragleave, false);
dropbox.addEventListener("drop", drop, false);

function dragenter(e) {
    dropbox.classList.add('hover');

    e.stopPropagation();
    e.preventDefault();
}

function dragover(e) {
    dropbox.classList.add('hover');
    e.stopPropagation();
    e.preventDefault();
}

function drop(e) {
    e.stopPropagation();
    e.preventDefault();

    dropbox.classList.remove('hover');
    dropbox.classList.add('drop');

    text.hidden = true;
    text2.hidden = true;
    icon.remove();

    let dt = e.dataTransfer;
    let files = dt.files;
    fileInput.files = dt.files;

    handleFiles(files);
}

function dragleave(e) {
    dropbox.classList.remove('hover');
    return false;
}


function onFileLoad(event) {
    dropbox.classList.remove('hover');
    dropbox.classList.add('drop');

    text.hidden = true;
    text2.hidden = true;
    icon.remove();

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

