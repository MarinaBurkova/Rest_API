package com.example.rest_api;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ApiController {
    private User user;
    // GET запрос
    @GetMapping("/data")
    public User getStaticData() {
        user = new User("User",  "User_Password");
        return user;
    }

    // POST запрос
    @PostMapping("/login")
    public User login(@RequestBody User requestUser) {
        if ( requestUser.login == null || requestUser.password == null ||  requestUser.login.isEmpty() || requestUser.password.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login and password are required");
        }
        return requestUser;
    }
}