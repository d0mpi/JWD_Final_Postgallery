let postContainer = document.querySelector(".main-box-inline-block");


function buildAndAddPostArrayToDOM(posts = []){
    for(let post of posts){
        const postEl = document.createElement('div');
        postEl.classList.add('card-box');
        postEl.id = post.id;
        let date = new Date(post.createdAt);
        let hashtags = "";
        let likeID = "like-check-"+post.id;
        let hide="hide";
        let likeHide = "hide";
        let checked = "";
        if(registeredUsersArray.isUserRegistered()) {
            likeHide = "flex";
            if(postArray.isLikedByUser(post.id,registeredUsersArray.getRegisteredUser().username)) {
                checked = "checked";
            }
            if(String(registeredUsersArray.getRegisteredUser().username) === (post.author)) {
                hide = "";
            }
        }

        post.hashtags.forEach((str) => (hashtags += "#" + String(str) + " "));

        postEl.innerHTML =
            `         <div class="card-main-col">
                        <div class="card-main-box col-full">
                            <div class="card-img-box">
                                <img src="${post.photoLink}" alt="samolet" class="card-img">
                            </div>
                            <div class="card-text-box">
                                <ul class="card-text-top font-card">
                                    <li><span>Model:</span> ${post.model}</li>
                                    <li><span>Type:</span> ${post.type}</li>
                                    <li><span>Length:</span> ${post.length}</li>
                                    <li><span>Wingspan:</span> ${post.wingspan}</li>
                                    <li><span>Height:</span> ${post.height}</li>
                                    <li><span>Origin:</span> ${post.origin}</li>
                                    <li><span>Crew:</span> ${post.crew}</li>
                                    <li><span>Max speed:</span> ${post.speed} km/h</li>
                                    <li><span>Flying dist:</span> ${post.dist} km</li>
                                    <li><span>Price:</span> ${post.price}$</li>
                                    <li>
                                        <hr class="card-text-hr col-full">
                                    </li>
                                </ul>
                                <div class="card-text-bottom-box">
                                    <ul class="card-text-bottom-left">
                                        <li class="card-text-user"> ${post.author} </li>
                                        <li class="card-id"> id: ${post.id}</li>
                                        <li class="card-text-time"> ${date.toLocaleString()}</li>
                                    </ul>

                                    <input id="${likeID}" class="like-check ${likeHide}" type="checkbox" ${checked} >
                                    <label for="like-check" class="like-label ${likeHide}"></label>
                                </div>

                            </div>
                        </div>

                        <div class="card-hashtags col-full">
                            ${hashtags}
                        </div>
                    </div>

                    <div class="card-buttons-col ${hide}">
                        <button  class="card-edit-button"> </button>
                        <button  class="card-delete-button" ></button>
                    </div>`;

        postContainer.append(postEl);
    }
}

function redrawFilterPostFeed(){
    while (postContainer.firstChild) {
        postContainer.removeChild(postContainer.firstChild);
    }

}





