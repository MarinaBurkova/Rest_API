package com.example.rest_api;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;

@RestController
public class ApiController {
    private App_DB appDB;


    // GET запрос
    @GetMapping("/user/{login}")
    public App_DB selByLogin(@PathVariable String login) {
        appDB = new App_DB();
        try {
            appDB.selectByLogin(login);
            return appDB;
        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }

    // POST запрос
    @PostMapping("/login")
    public String login(@RequestBody App_DB reqAppDB){
        reqAppDB.insertData();
        if ( reqAppDB.login == null || reqAppDB.password == null || reqAppDB.date == null || reqAppDB.email == null || reqAppDB.login.isEmpty() || reqAppDB.password.isEmpty() || reqAppDB.date.isEmpty() || reqAppDB.email.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data required");
        }
        return "User \"" + reqAppDB.login + "\" added.";
    }
}