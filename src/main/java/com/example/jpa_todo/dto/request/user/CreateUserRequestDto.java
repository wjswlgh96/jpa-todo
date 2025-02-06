package com.example.jpa_todo.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateUserRequestDto {

    @NotBlank(message = "이름은 필수값입니다.")
    @Size(max = 10, message = "이름은 최대 10글자까지 가능합니다.")
    private final String username;

    @NotBlank(message = "이메일은 필수값입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private final String email;

    @NotBlank(message = "비밀번호는 필수값입니다.")
    private final String password;

    public CreateUserRequestDto(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
