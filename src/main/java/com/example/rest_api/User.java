package com.example.rest_api;

import java.time.LocalDateTime;

public class User {
    public String login;
    public String password;
    public String date = LocalDateTime.now().toString();

    public User() {
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    /*
    public String toStringDate(){
        return "{" +
                "\"login\": \"" + login + '\"' +
                ", \"date\": \"" + date + '\"' +
                '}';
    }
    */

}
