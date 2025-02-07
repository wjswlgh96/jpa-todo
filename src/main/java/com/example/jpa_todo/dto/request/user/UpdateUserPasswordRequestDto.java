package com.example.jpa_todo.dto.request.user;

import com.example.jpa_todo.common.Const;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UpdateUserPasswordRequestDto {

    @NotBlank(message = "현재 비밀번호는 필수값입니다.")
    @Pattern(
            regexp = Const.PASSWORD_PATTERN,
            message = "비밀번호는 4~12 자리만 가능하며, 최소 하나의 대문자와 하나의 특수문자(!, @, #, $, %, ^, &, *)를 포함해야 합니다."
    )
    @Schema(description = "유저의 기존 비밀번호 (필수)", example = "비밀번호는 4~12 자리만 가능하며, 최소 하나의 대문자와 하나의 특수문자(!, @, #, $, %, ^, &, *)를 포함해야 합니다.")
    private final String oldPassword;

    @NotBlank(message = "새 비밀번호는 필수값입니다.")
    @Pattern(
            regexp = Const.PASSWORD_PATTERN,
            message = "비밀번호는 4~12 자리만 가능하며, 최소 하나의 대문자와 하나의 특수문자(!, @, #, $, %, ^, &, *)를 포함해야 합니다."
    )
    @Schema(description = "유저의 새로운 비밀번호 (필수)", example = "비밀번호는 4~12 자리만 가능하며, 최소 하나의 대문자와 하나의 특수문자(!, @, #, $, %, ^, &, *)를 포함해야 합니다.")
    private final String newPassword;

    public UpdateUserPasswordRequestDto(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

}
