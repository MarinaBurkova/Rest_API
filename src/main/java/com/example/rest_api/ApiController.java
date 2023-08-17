package com.example.rest_api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.sql.SQLException;
import java.io.IOException;

@RestController
public class ApiController {
    @GetMapping("/user/{login}")
    public ResponseEntity<User> getUser(@PathVariable String login) throws Exception{
        try {
            User user = App_DB.selectByLogin(login);

            if (user == null){
                throw new UserNotFoundException("DB problems");
            }
            FileService.writeToFile(user, "user_data.txt");
            return ResponseEntity.ok(user);
        } catch (SQLException | UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/randomString")
    public ResponseEntity<String> getRandomString() {
        try {
            FileService fileReaderService = new FileService();
            String randomString = fileReaderService.getRandomString("user_data.txt");
            return ResponseEntity.ok(randomString);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error reading");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> postUser(@RequestBody User user) throws Exception{
        try {
            String result = App_DB.insertData(user);

            return ResponseEntity.ok(result);
        }catch (SQLException | UserNotFoundException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

}
