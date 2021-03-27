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
            model: "F-35A Lightning II",
            type: "fighter",
            length: 15.57,
            wingspan: 10.67,
            height: 4.38,
            origin: "USA",
            crew: 1,
            speed: 1930,
            dist: 2200,
            price: '84.300.000',
            createdAt: new Date(),
            author: 'admin',
            photoLink: 'images/planes/F-35A.jpg',
            hashtags: ["F35", "USA", "Lockheed_Martin", "Lightning"],
            likes: []
        },
        {
            id: '2',
            model: "Su-57",
            type: "multirole",
            length: 19.4,
            wingspan: 14,
            height: 4.8,
            origin: "Russia",
            crew: 1,
            speed: 2600,
            dist: 5500,
            price: '35.000.000',
            createdAt: new Date(),
            author: 'admin',
            photoLink: 'images/planes/SU-57.jpg',
            hashtags: ["Russia", "stealth", "Sukhoi", "Felon"],
            likes: ["admin"]
        },
        {
            id: '3',
            model: "F-22 Raptor",
            type: "fighter",
            length: 18.9,
            wingspan: 13.56,
            height: 5.09,
            origin: "USA",
            crew: 1,
            speed: 2410,
            dist: 2960,
            price: '146.200.000',
            createdAt: new Date(),
            author: 'admin',
            photoLink: 'images/planes/F-22.jpg',
            hashtags: ["USA", "fighter", "Lockheed_Martin", "Raptor"],
            likes: []
        },
        {
            id: '4',
            model: "An-124-100 Ruslan",
            type: "transport",
            length: 69.1,
            wingspan: 73.3,
            height: 21.08,
            origin: "USSR",
            crew: 8,
            speed: 865,
            dist: 11500,
            price: '300.000.000',
            createdAt: new Date(),
            author: 'admin',
            photoLink: 'images/planes/An-124-100.jpg',
            hashtags: ["Russia", "USSR", "Ruslan", "Condor", "Antonov"],
            likes: ["admin"]
        },
        {
            id: '5',
            model: "B-2 Spirit",
            type: "bomber",
            length: 20.90,
            wingspan: 52.12,
            height: 5.1,
            origin: "USA",
            crew: 2,
            speed: 1010,
            dist: 11100,
            price: '2.100.000.000',
            createdAt: new Date(),
            author: 'admin',
            photoLink: 'images/planes/B-2.jpg',
            hashtags: ["Spirit", "Northrop", "USA", "stealth"],
            likes: []
        },
        {
            id: '6',
            model: "Tu-160",
            type: "bomber",
            length: 54.1,
            wingspan: "35.6 - 55.7",
            height: 13.1,
            origin: "USSR",
            crew: 4,
            speed: 2220,
            dist: 12300,
            price: '600.000.000',
            createdAt: new Date(),
            author: 'admin',
            photoLink: 'images/planes/Tu-160.jpg',
            hashtags: ["USSR", "Russia", "Blackjack", "Tupolev"],
            likes: []
        },
        {
            id: '7',
            model: "A-10 Thunderbolt II",
            type: "attack",
            length: 16.26,
            wingspan: 17.53,
            height: 4.47,
            origin: "USA",
            crew: 1,
            speed: 722,
            dist: "740 (max: 4647)",
            price: '18.800.000',
            createdAt: new Date(),
            author: 'admin',
            photoLink: 'images/planes/A-10.jpg',
            hashtags: ["USA", "Thunderbolt", "Warthog", "Fairchild_Republic"],
            likes: []
        },
        {
            id: '8',
            model: "E-2 Hawkeye",
            type: "airborne early warning",
            length: 17.6,
            wingspan: 24.56,
            height: 5.58,
            origin: "USA",
            crew: 5,
            speed: 650,
            dist: 2708,
            price: '80.000.000',
            createdAt: new Date(),
            author: 'admin',
            photoLink: 'images/planes/E-2.jpg',
            hashtags: ["USA", "Hawkeye", "Grumman"],
            likes: []
        },
        {
            id: '9',
            model: "EA-18G Growler",
            type: "electronic warfare",
            length: 18.31,
            wingspan: 13.62,
            height: 4.88,
            origin: "USA",
            crew: 2,
            speed: 1900,
            dist: 2346,
            price: '101.000.000',
            createdAt: new Date(),
            author: 'admin',
            photoLink: 'images/planes/EA-18G.jpg',
            hashtags: ["USA", "Growler", "Boeing"],
            likes: ["admin"]
        },
        {
            id: '10',
            model: "A400M Atlas",
            type: "transport",
            length: 45.1,
            wingspan: 42.40,
            height: 14.7,
            origin: "EU",
            crew: 4,
            speed: 802,
            dist: 7870,
            price: '145.000.000',
            createdAt: new Date(),
            author: 'admin',
            photoLink: 'images/planes/A400M.jpg',
            hashtags: ["EU", "Atlas", "Airbus"],
            likes: []
        },
        {
            id: '11',
            model: "Kawasaki P-1",
            type: "maritime patrol",
            length: 38,
            wingspan: 35.4,
            height: 12.1,
            origin: "Japan",
            crew: 11,
            speed: 996,
            dist: 8000,
            price: '208.300.000',
            createdAt: new Date(),
            author: 'admin',
            photoLink: 'images/planes/P-1.jpg',
            hashtags: ["Japan", "Kawasaki", "maritime_force"],
            likes: []
        }
    ];

    constructor(localPosts) {
        if (localPosts === null || localPosts === undefined || !Array.isArray(localPosts) || localPosts.length === 0) {
            this.restore();
        } else {
            this.#postList = localPosts;
            // localStorage.setItem("postList", JSON.stringify([]));
        }
        console.log("Post Collection was created!!!");
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

    isLikedByUser(id, username) {
        return this.get(id).likes.includes(String(username));
    }

    likePost(id, username) {
        if (this.isLikedByUser(id, username)) {
            console.log("index " + this.get(id).likes.indexOf(String(username)));
            this.get(id).likes.splice(this.get(id).likes.indexOf(String(username)),1);
        } else {
            this.get(id).likes.push(String(username));
        }
        this.save();
    }

    getPostArray() {
        return this.#postList;
    }

    getLastId() {
        return parseInt(this.#postList[this.#postList.length - 1].id);
    }

    static clear() {
        PostCollection.setPostArrayToLocale([]);
        console.log("Posts array is empty!!!");
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
                if (filterClass.date != null) {
                    let filterDate = new Date(filterClass.date);
                    // console.log(" " + new Date(post.createdAt.slice(0,10)));
                    let postDate = new Date(post.createdAt);
                    // console.log(filterDate + " " + postDate);
                    if (filterDate.getFullYear() !== postDate.getFullYear())
                        fit = false;
                    else if (filterDate.getMonth() !== postDate.getMonth())
                        fit = false;
                    else if (filterDate.getDate() !== postDate.getDate())
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





