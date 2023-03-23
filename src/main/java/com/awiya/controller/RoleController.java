package com.awiya.controller;

import com.awiya.entities.Role;
import com.awiya.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;


    @PostMapping("/createRole")
    public Role createRole(@RequestBody Role role){
        return roleService.createRole(role);
    }
}
