package com.example.jpa_todo.domain.auth.service;

import com.example.jpa_todo.config.PasswordEncoder;
import com.example.jpa_todo.domain.user.service.UserService;
import com.example.jpa_todo.domain.user.dto.response.UserResponseDto;
import com.example.jpa_todo.domain.user.entity.User;
import com.example.jpa_todo.common.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDto signUp(String username, String email, String password) {
        if (userService.existsByEmail(email)) {
            throw new ApplicationException(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다.");
        }

        User user = new User(username, email, passwordEncoder.encode(password));
        User savedUser = userService.save(user);
        return UserResponseDto.toDto(savedUser);
    }

    public UserResponseDto login(String email, String password) {
        User user = userService.findByEmailOrThrow(email);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ApplicationException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        return UserResponseDto.toDto(user);
    }
}
