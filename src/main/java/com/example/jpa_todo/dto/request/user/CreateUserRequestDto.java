package com.example.jpa_todo.dto.request.user;

import lombok.Getter;

@Getter
public class CreateUserRequestDto {

    private final String username;

    private final String email;

    private final String password;

    public CreateUserRequestDto(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
