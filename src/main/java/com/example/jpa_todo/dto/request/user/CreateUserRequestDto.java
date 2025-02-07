package com.example.jpa_todo.dto.request.user;

import com.example.jpa_todo.common.Const;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateUserRequestDto {

    @NotBlank(message = "이름은 필수값입니다.")
    @Size(max = 10, message = "이름은 최대 10글자까지 가능합니다.")
    @Schema(description = "유저의 이름 (필수)", example = "홍길동")
    private final String username;

    @NotBlank(message = "이메일은 필수값입니다.")
    @Pattern(
            regexp = Const.EMAIL_PATTERN,
            message = "이메일 형식이 올바르지 않습니다."
    )
    @Schema(description = "유저의 이메일, 로그인때 사용함 (필수)", example = "hong@example.com")
    private final String email;

    @NotBlank(message = "비밀번호는 필수값입니다.")
    @Pattern(
            regexp = Const.PASSWORD_PATTERN,
            message = "비밀번호는 4~16 자리만 가능하며, 최소 하나의 대문자와 하나의 특수문자(!, @, #, $, %, ^, &, *)를 포함해야 합니다."
    )
    @Schema(description = "유저의 비밀번호 (필수)", example = "비밀번호는 4~16 자리만 가능하며, 최소 하나의 대문자와 하나의 특수문자(!, @, #, $, %, ^, &, *)를 포함해야 합니다.")
    private final String password;

    public CreateUserRequestDto(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
