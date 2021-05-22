let pageName = location.href.split("/").slice(-1);
console.log(pageName);

let currentMenuBtn = document.getElementById("header-"+ pageName +"-btn");
currentMenuBtn.classList.add("menu-href-active");