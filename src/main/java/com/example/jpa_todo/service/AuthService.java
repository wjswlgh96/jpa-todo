package com.example.jpa_todo.service;

import com.example.jpa_todo.config.PasswordEncoder;
import com.example.jpa_todo.dto.response.user.UserResponseDto;
import com.example.jpa_todo.entity.User;
import com.example.jpa_todo.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto signUp(String username, String email, String password) {
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
