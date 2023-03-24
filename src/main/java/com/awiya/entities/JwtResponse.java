package com.awiya.entities;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class JwtResponse {


    private String jwtToken;
    private User user;

    public JwtResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}

