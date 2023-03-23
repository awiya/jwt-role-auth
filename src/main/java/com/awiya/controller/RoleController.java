package com.awiya.controller;

import com.awiya.entities.Role;
import com.awiya.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
