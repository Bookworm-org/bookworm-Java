// In file: src/main/java/com/bookworm/dto/response/JwtResponse.java

package com.bookworm.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Integer id; // Field for Customer ID
    private String name; // Field for Customer Name

    // Updated constructor
    public JwtResponse(String accessToken, Integer id, String name) {
        this.token = accessToken;
        this.id = id;
        this.name = name;
    }
}