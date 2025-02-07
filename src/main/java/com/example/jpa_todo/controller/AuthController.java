package com.example.jpa_todo.controller;

import com.example.jpa_todo.common.Const;
import com.example.jpa_todo.dto.request.user.CreateUserRequestDto;
import com.example.jpa_todo.dto.request.user.LoginRequestDto;
import com.example.jpa_todo.dto.response.user.UserResponseDto;
import com.example.jpa_todo.service.AuthService;
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
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "유저가 로그인 하여 API 사용 권한을 얻습니다.")
    public ResponseEntity<Void> login(
            @Valid @RequestBody LoginRequestDto requestDto,
            HttpServletRequest request
    ) {
        UserResponseDto user = authService.login(requestDto.getEmail(), requestDto.getPassword());
        HttpSession session = request.getSession();
        session.setAttribute(Const.LOGIN_USER, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
