class RegistrationCollection {

    #registeredUsers = [
        {
            id: '1',
            username: "admin",
            password: "admin",
            email: "admin@gmail.com"
        }
    ];

    constructor(localUsers) {
        if (localUsers === null || localUsers === undefined || !Array.isArray(localUsers) || localUsers.length === 0) {
            this.save();
        } else {
            this.#registeredUsers = localUsers;
        }
        console.log("RegistrationCollection was created!!!");
    }

    get(id) {
        let user = this.#registeredUsers.find(item => String(item.id) === String(id));
        console.log("User " + user.id + " was returned!!");
        return user;
    }

    getUsersArray() {
        return this.#registeredUsers;
    }

    static clear() {
        RegistrationCollection.setRegisteredUsersFromLocale([]);
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
        this.save();
        console.log("User " + user.id + " was added");
    }

    save() {
        RegistrationCollection.setRegisteredUsersFromLocale(this.#registeredUsers);
    }

    static getRegisteredUsersFromLocale() {
        return JSON.parse(localStorage.getItem("registeredUsers"));
    }

    static setRegisteredUsersFromLocale(array) {
        return localStorage.setItem("registeredUsers", JSON.stringify(array));
    }
}

let registeredUsersArray = new RegistrationCollection(RegistrationCollection.getRegisteredUsersFromLocale());
