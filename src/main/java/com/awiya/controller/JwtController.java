package com.awiya.controller;

import com.awiya.entities.JwtRequest;
import com.awiya.entities.JwtResponse;
import com.awiya.entities.User;
import com.awiya.services.JwtService;
import com.awiya.utils.JwtTokenUtil;
import com.awiya.services.JwtUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class JwtController {


    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        return jwtService.createJwtToken(jwtRequest);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new Exception("Invalid username or password");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestBody User userDto) throws Exception {
        if (userDetailsService instanceof JwtUserDetailsService) {
            JwtUserDetailsService jwtUserDetailsService = (JwtUserDetailsService) userDetailsService;
            User existingUser = jwtUserDetailsService.getUserByUsername(userDto.getUsername());
            if (existingUser != null) {
                throw new Exception("User already exists with username: " + userDto.getUsername());
            }

            User newUser = new User();
            newUser.setUsername(userDto.getUsername());
            newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
            jwtUserDetailsService.saveUser(newUser);
            return ResponseEntity.ok(newUser);
        }

        throw new UsernameNotFoundException("User details service is not an instance of JwtUserDetailsService");
    }
}

