let fileInput = document.getElementById("file");
let image = document.getElementById("add_img");
let dropbox = document.getElementById("drag-and-drop");
let text = document.querySelector(".drag-text");
let fileName;

dropbox.addEventListener("dragenter", dragenter, false);
dropbox.addEventListener("dragover", dragover, false);
dropbox.addEventListener("dragleave", dragleave, false);
dropbox.addEventListener("drop", drop, false);

function dragenter(e) {
    jQuery('#drag-and-drop').addClass('hover');

    e.stopPropagation();
    e.preventDefault();
}

function dragover(e) {
    jQuery('#drag-and-drop').addClass('hover');
    e.stopPropagation();
    e.preventDefault();
}

function drop(e) {
    e.stopPropagation();
    e.preventDefault();

    jQuery('#drag-and-drop').removeClass('hover');
    jQuery('#drag-and-drop').addClass('drop');

    text.remove();
    let dt = e.dataTransfer;
    let files = dt.files;
    fileInput.files = dt.files;

    handleFiles(files);
}

function dragleave(e) {
    jQuery('#drag-and-drop').removeClass('hover');
    return false;
}


$(document).ready(function () {
    $('.drop-button').click(function () {
        $('#file').trigger('click');
    });
});

function onFileLoad(event) {
    fileName = fileInput.files.item(0).name;
    jQuery('#drag-and-drop').removeClass('hover');
    jQuery('#drag-and-drop').addClass('drop');

    text.remove();
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

