package com.example.rest_api;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ApiController {

    // GET запрос
    @GetMapping("/data")
    public Map<String, String> getStaticData() {
        Map<String, String> data = new HashMap<>();
        data.put("PASSWORD", "USER_PASSWORD");
        data.put("LOGIN", "USER");
        return data;
    }

    // POST запрос
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> request) {
        String login = request.get("login");
        String password = request.get("password");

        Map<String, Object> response = new HashMap<>();
        response.put("login", login);
        response.put("date", LocalDateTime.now().toString());
        return response;
    }
}