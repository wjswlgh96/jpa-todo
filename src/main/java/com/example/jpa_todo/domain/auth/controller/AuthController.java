package com.example.jpa_todo.domain.auth.controller;

import com.example.jpa_todo.common.Const;
import com.example.jpa_todo.domain.user.dto.request.CreateUserRequestDto;
import com.example.jpa_todo.domain.user.dto.request.LoginRequestDto;
import com.example.jpa_todo.domain.user.dto.response.UserResponseDto;
import com.example.jpa_todo.common.exception.ApplicationException;
import com.example.jpa_todo.domain.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth API", description = "회원 인증 관련 API")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    @Operation(summary = "회원 가입", description = "새로운 유저를 등록합니다.")
    public ResponseEntity<UserResponseDto> signUp(@Valid @RequestBody CreateUserRequestDto requestDto) {
        UserResponseDto responseDto = authService.signUp(
                requestDto.getUsername(),
                requestDto.getEmail(),
                requestDto.getPassword()
        );
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "유저가 로그인 하여 API 사용 권한을 얻습니다.")
    public ResponseEntity<Void> login(
            @Valid @RequestBody LoginRequestDto requestDto,
            HttpServletRequest request
    ) {
        UserResponseDto user = authService.login(requestDto.getEmail(), requestDto.getPassword());
        HttpSession session = request.getSession(false);    // false 를 사용하여 session 이 없을 시 null 반환
        if (session != null && session.getAttribute(Const.LOGIN_USER) != null) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "이미 로그인된 상태입니다.");
        }

        session = request.getSession(true);
        session.setAttribute(Const.LOGIN_USER, user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "유저 로그 아웃")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return ResponseEntity.ok().build();
    }
}
