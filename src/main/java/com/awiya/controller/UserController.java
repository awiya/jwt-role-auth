package com.awiya.controller;

import com.awiya.entities.User;
import com.awiya.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostConstruct
    public void initUsersAndRoles() {
        userService.initUsersAndRoles();
    }

    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/forAdmin")
    public String authAdmin() {
        return "THIS URL IS ONLY FOR ADMIN";
    }

    @GetMapping("/forUser")
    public String authUser() {
        return "THIS URL IS ONLY FOR USER";
    }
}
