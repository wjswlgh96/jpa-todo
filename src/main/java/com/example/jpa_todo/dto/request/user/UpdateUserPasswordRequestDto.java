package com.example.jpa_todo.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateUserPasswordRequestDto {

    @NotBlank(message = "현재 비밀번호는 필수값입니다.")
    private final String oldPassword;

    @NotBlank(message = "새 비밀번호는 필수값입니다.")
    private final String newPassword;

    public UpdateUserPasswordRequestDto(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

}
