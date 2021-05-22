class RegistrationCollection {
    #registeredUser = {
        id: "",
        username: "",
        password: "",
        email: ""
    }

    #registeredUsers = [
        {
            id: '1',
            username: "admin",
            password: "admin",
            email: "admin@gmail.com"
        }
    ];

    constructor(localUsers, localUser) {
        if (localUsers === null || localUsers === undefined || !Array.isArray(localUsers) || localUsers.length === 0) {
            this.saveArray();
        } else {
            this.#registeredUsers = localUsers;
        }
        if (localUser === null || localUser === undefined || localUser.id === "" || localUser.username === "") {
            this.saveUser();
        } else {
            this.#registeredUser = localUser;
        }
        console.log("RegistrationCollection was created!!!");
    }

    getUserFromArray(id) {
        let user = this.#registeredUsers.find(item => String(item.id) === String(id));
        console.log("User " + user.id + " was returned!!");
        return user;
    }

    getUserIDByUsername(username) {
        let user = this.#registeredUsers.find(item => String(item.username) === String(username));
        return parseInt(user.id);
    }


    getRegisteredUser() {
        if (this.#registeredUser.id === "" || this.#registeredUser.username === "" || this.#registeredUser.email === "" || this.#registeredUser.password === "") {
            return null;
        } else {
            return this.#registeredUser;
        }
    }

    isUserRegistered() {
        if (this.#registeredUser.id === "" || this.#registeredUser.username === "" || this.#registeredUser.email === "" || this.#registeredUser.password === "") {
            return false;
        } else {
            return true;
        }
    }

    signOut() {
        this.#registeredUser = {
            id: "",
            username: "",
            password: "",
            email: ""
        };
        this.saveUser();
        alert("Signing out!!")
    }

    getLastId() {
        return parseInt(this.#registeredUsers[this.#registeredUsers.length - 1].id);
    }

    doesUserExist(username) {
        let user = this.#registeredUsers.find(item => String(item.username) === String(username));
        return user == null;
    }

    doesEmailExist(email) {
        let user = this.#registeredUsers.find(item => String(item.email) === String(email));
        return user == null;
    }

    setRegisteredUser(id) {
        this.#registeredUser = this.#registeredUsers.find(item => String(item.id) === String(id));
        this.saveUser();
    }

    getUsersArray() {
        return this.#registeredUsers;
    }

    static clear() {
        RegistrationCollection.setRegisteredUsersToLocale([]);
        console.log("Users array is empty!!!");
    }

    static validateUser(user) {
        if (user.hasOwnProperty('id') &&
            user.hasOwnProperty('username') &&
            user.hasOwnProperty('email')) {
            console.log("User is valid");
            return true;
        } else {
            console.log("User is not valid.");
            return false;
        }
    }

    add(user) {
        if (RegistrationCollection.validateUser(user)) {
            this.#registeredUsers.push(user);
        } else {
            alert("Error addUser");
        }
        this.saveArray();
        console.log("User " + user.id + " was added");
    }

    saveArray() {
        RegistrationCollection.setRegisteredUsersToLocale(this.#registeredUsers);
    }

    saveUser() {
        RegistrationCollection.setRegisteredUserToLocale(this.#registeredUser);
    }

    static getRegisteredUsersFromLocale() {
        return JSON.parse(localStorage.getItem("registeredUsers"));
    }

    static setRegisteredUsersToLocale(array) {
        return localStorage.setItem("registeredUsers", JSON.stringify(array));
    }

    static getRegisteredUserFromLocale() {
        return JSON.parse(localStorage.getItem("registeredUser"));
    }

    static setRegisteredUserToLocale(user) {
        return localStorage.setItem("registeredUser", JSON.stringify(user));
    }
}

let registeredUsersArray = new RegistrationCollection(RegistrationCollection.getRegisteredUsersFromLocale(),
    RegistrationCollection.getRegisteredUserFromLocale());
