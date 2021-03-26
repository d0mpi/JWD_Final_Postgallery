let skipCounter = postArray.getPage(0, 10, filterClass).length;

buildAndAddPostArrayToDOM(postArray.getPage());

let moreBtn = document.getElementById("more_btn");
let filterBtn = document.getElementById("filter_btn");
let filterForm = document.forms.filter;


/**
 * Листнер кнопки добавляющей 10 постов
 */
if (postArray.getPage(skipCounter, 1, filterClass).length === 0) {
    moreBtn.style.display = "none";
}


/**
 *  Листнер кнопок удаления
 */
[...document.querySelectorAll('.card-delete-button')].forEach(function (item) {
    item.addEventListener('click', handleDelete);
});

function handleDelete(event) {
    const result = confirm('Are you sure?');
    if (!result) {
        return false;
    }
    let cardBox = event.currentTarget.parentElement.parentElement;
    let id = cardBox.id;
    postArray.remove(id);
    cardBox.remove();
}

/**
 *  Листнер кнопок редактирования
 */
[...document.querySelectorAll('.card-edit-button')].forEach(function (item) {
    item.addEventListener('click', handleEdit);
});

function handleEdit(event) {
    let id = event.currentTarget.parentElement.parentElement.id;
    localStorage.setItem("editPostID", JSON.stringify(id));
    window.location.href = "edit.html";
}


/**
 *  Листнер кнопки фильтра
 */
function handleFilterSubmit(event) {
    let redraw = filterForm.filter_author_checkbox.checked ||
        filterForm.filter_hashtags_checkbox.checked ||
        filterForm.filter_date_checkbox.checked;

    if (filterForm.filter_author_checkbox.checked) {
        filterClass.author = filterForm.filter_author_text.value;
        alert("author checked");
    } else {
        filterClass.author = "";
    }
    if (filterForm.filter_date_checkbox.checked) {
        filterClass.date = new Date(filterForm.filter_date_text.value);
        alert("date checked" + filterClass.date);
    } else {
        filterClass.date = null;
    }
    if (filterForm.filter_hashtags_checkbox.checked) {
        filterClass.hashtags = filterForm.filter_hashtags_text.value;
        alert("hash checked");
    } else {
        filterClass.hashtags = "";
    }

    redrawFilterPostFeed();
    let tempArray = postArray.getPage(0, 10, filterClass);
    skipCounter = tempArray.length;
    if (postArray.getPage(skipCounter, 1, filterClass).length !== 0) {
        moreBtn.style.display = "flex";
    }
    buildAndAddPostArrayToDOM(tempArray);
}

filterBtn.addEventListener('click', handleFilterSubmit);


function handleMorePosts() {
    buildAndAddPostArrayToDOM(postArray.getPage(skipCounter, 10, filterClass));
    skipCounter += 10;
    if (postArray.getPage(skipCounter, 1, filterClass).length === 0) {
        moreBtn.style.display = "none";
    }
    [...document.querySelectorAll('.card-delete-button')].forEach(function (item) {
        item.addEventListener('click', handleDelete);
    });
    [...document.querySelectorAll('.card-edit-button')].forEach(function (item) {
        item.addEventListener('click', handleEdit);
    });
}

moreBtn.addEventListener('click', handleMorePosts);











