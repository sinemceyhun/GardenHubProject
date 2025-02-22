package org.example.data.entity;

import org.example.data.entity.Users;
public class CurrentUser {
    private static CurrentUser instance;
    private Users loggedInUser;

    private CurrentUser() { }

    public static CurrentUser getInstance() {
        if (instance == null) {
            instance = new CurrentUser();
        }
        return instance;
    }

    public Users getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(Users user) {
        this.loggedInUser = user;
    }

    public void clear() {
        this.loggedInUser = null;
    }
}
