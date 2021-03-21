
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
            createdAt: new Date('2021-03-15T23:00:00'),
            author: 'Иванов Иван',
            photoLink: 'https://www.pressball.by/images/stories/2020/03/20200310231542.jpg',
            hashTags: ["first", "second", "third"],
            likeCount: 0
        }
    ];

    constructor(postList = []) {
        this.#postList = [];
        for (let post of postList) {
            if (PostCollection.validatePost(post)) {
                this.#postList.push(post);
            }
        }
        console.log("Object was created!!!")
    }

    addAll(postList) {
        let invalidPostsList = [];
        for (let post in postList) {
            if (PostCollection.validatePost(post)) {
                this.#postList.push(post);
            } else {
                invalidPostsList.push(post);
            }
        }
        return invalidPostsList;
    }

    get(id) {
        id = String(id);
        let post = this.#postList.find(item => item.id === id);
        console.log("Post " + post.id + " was returned!!");
        return post;
    }

    clear() {
        this.#postList.length = 0;
        console.log("Array is empty!!!")
    }

    getPage(skip = 0, top = 10, filterConfig = null) {
        let resultArr = [];

        for (let post of this.#postList) {
            if (skip > 0) {
                skip--;
                continue;
            }

            if (top > 0) {
                resultArr.push(post);
                top--;

            }

        }
        console.log("Получение отфильтрованного массива");
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
            post.hasOwnProperty('likeCount') &&
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

        console.log("Post " + postForEdit.id + "has been editing");
    }
    add(post) {
        if (PostCollection.validatePost(post)) {
            this.#postList.push(post);
        } else {
            alert("Error addPost");
        }
        console.log("Post " + post.id + " was added")
    }

    remove(id) {
        id = String(id);
        let idx = posts.findIndex(function (element) {
            return element.id === id;
        })
        let temp = posts[idx].id;
        if (idx !== -1) {
            this.#postList.splice(idx, 1);
        }
        console.log("Post " + temp + " was deleted");
    }

    like(id) {
        id = String(id);
        let post = this.#postList.find(item => item.id === id);
        post.likeCount++;
    }
}

