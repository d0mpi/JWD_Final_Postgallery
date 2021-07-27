let pageName = location.href.split("=").slice(-1).toString().split('_')[0];

let currentMenuBtn = document.getElementById("header-" + pageName + "-btn");
if(currentMenuBtn != null){
    currentMenuBtn.classList.add("menu-href-active");
}
