let filterClass = {
    author: "",
    date: null,
    hashtags: ""
}

localStorage.setItem("filter", JSON.stringify(filterClass));

class PostCollection {

    #postList = [
        {
            id: '1',
            model: "F-35 Lightning II",
            type: "fighter",
            length: 15.57,
            wingspan: 10.67,
            height: 4.38,
            origin: "USA",
            crew: 1,
            speed: 1930,
            dist: 2200,
            price: 84300000,
            createdAt: new Date(),
            author: 'Иванов Иван',
            photoLink: 'https://www.pressball.by/images/stories/2020/03/20200310231542.jpg',
            hashtags: ["first", "second", "third"],
            likes: []
        }
    ];

    constructor(localePosts) {
        if (localePosts === null || localePosts === undefined || !Array.isArray(localePosts) || localePosts.length === 0) {
            this.restore();
        } else {
            this.#postList = localePosts;
            // localStorage.setItem("postList", JSON.stringify([]));
        }
        console.log("Object was created!!!");
    }

    addAll(postList) {
        let invalidPostsList = [];
        for (let post of postList) {
            if (PostCollection.validate(post)) {
                this.#postList.push(post);
            } else {
                invalidPostsList.push(post);
            }
        }
        this.save();
        return invalidPostsList;
    }

    get(id) {
        let post = this.#postList.find(item => String(item.id) === String(id));
        console.log("Post " + post.id + " was returned!!");
        return post;
    }

    getPostArray() {
        return this.#postList;
    }

    getLastId() {
        return parseInt(this.#postList[this.#postList.length - 1].id);
    }

    static clear() {
        PostCollection.setPostArrayToLocale([]);
        console.log("Array is empty!!!");
    }

    getPage(skip = 0, top = 10, filterConfig = null) {
        let resultArr = [];
        if (filterConfig == null || (filterClass.author === "" && filterClass.date == null && filterClass.hashtags === "")) {
            // console.log("No filter");
            // console.log("Filtered feed : " + this.#postList.length);
            resultArr = this.#postList.slice(skip, skip + top);
            console.log("Получение нефильтрованного массива");
        } else {
            // console.log("Filter:" + "\"" + filterClass.author + "\"" + filterClass.hashtags + "\"" +"\"" + filterClass.date + "\"");
            resultArr = this.#postList.filter(function (post) {
                let fit = true;
                if (filterClass.author !== "") {
                    if (post.author !== filterClass.author)
                        fit = false;
                }
                if (filterClass.hashtags !== "") {
                    if (post.hashtags.indexOf(filterClass.hashtags) === -1)
                        fit = false;
                }
                if(filterClass.date != null) {
                    let filterDate = new Date(filterClass.date);
                    // console.log(" " + new Date(post.createdAt.slice(0,10)));
                    let postDate = new Date(post.createdAt);
                    // console.log(filterDate + " " + postDate);
                    if(filterDate.getFullYear() !== postDate.getFullYear())
                        fit = false;
                    else if(filterDate.getMonth() !== postDate.getMonth())
                        fit = false;
                    else if(filterDate.getDate() !== postDate.getDate())
                        fit = false;
                }
                return fit;
            });
            // console.log("Filtered feed : " + resultArr.length);
            resultArr = resultArr.slice(skip, skip + top);
            console.log("Получение отфильтрованного массива");
        }

        // Вернуть отфильтрованный и от не slice


        return resultArr;
    }

    static validate(post) {
        if (post.hasOwnProperty('id') &&
            post.hasOwnProperty('model') &&
            post.hasOwnProperty('type') &&
            post.hasOwnProperty('length') &&
            post.hasOwnProperty('wingspan') &&
            post.hasOwnProperty('height') &&
            post.hasOwnProperty('origin') &&
            post.hasOwnProperty('crew') &&
            post.hasOwnProperty('speed') &&
            post.hasOwnProperty('dist') &&
            post.hasOwnProperty('price') &&
            post.hasOwnProperty('createdAt') &&
            post.hasOwnProperty('hashtags') &&
            post.hasOwnProperty('likes') &&
            post.hasOwnProperty('author') &&
            post.hasOwnProperty('photoLink')) {
            console.log("Post is valid");
            return true;
        } else {
            console.log("Post is not valid.");
            return false;
        }
    }

    edit(id, post) {
        let postForEdit = this.get(id);
        for (let key in post) {
            if (postForEdit.hasOwnProperty(key)) {
                postForEdit[key] = post[key];
            }
        }
        this.save();
        console.log("Post " + postForEdit.id + "has been editing");
    }

    add(post) {
        if (PostCollection.validate(post)) {
            this.#postList.push(post);
        } else {
            alert("Error addPost");
        }
        this.save();
        console.log("Post " + post.id + " was added");
    }

    remove(id) {
        let idx = this.#postList.indexOf(this.#postList.find(item => String(item.id) === String(id)));

        let temp = this.#postList[idx].id;
        if (idx !== -1) {
            this.#postList.splice(idx, 1);
        }
        this.save();
        console.log("Post " + temp + " was deleted");
    }

    like(id, author) {
        id = String(id);
        let post = this.#postList.find(item => item.id === id);
        post.likes.push(author.toString());
    }

    save() {
        PostCollection.setPostArrayToLocale(this.#postList);
    }


    restore() {
        PostCollection.setPostArrayToLocale(this.#postList);
    }

    static getPostArrayFromLocale() {
        return JSON.parse(localStorage.getItem("postList"));
    }

    static setPostArrayToLocale(array) {
        return localStorage.setItem("postList", JSON.stringify(array));
    }
}

let postArray = new PostCollection(PostCollection.getPostArrayFromLocale());





