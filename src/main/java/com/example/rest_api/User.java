package com.example.rest_api;
import java.time.LocalDate;
import java.util.Date;

public class User {
    public String login;
    public String password;
    public String date = LocalDate.now().toString();
    public String email;

    public User() {
    }

    public User(String login, String password, Date date, String email) {
        this.login = login;
        this.password = password;
        this.date = String.valueOf(date);
        this.email = email;
    }
    public String toString() {
        return "{login : " + login +
                ", password : " + password +
                ", date : " + date +
                ", email : " + email + "}" + "\n";
    }
}
