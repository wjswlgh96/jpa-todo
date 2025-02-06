package com.example.jpa_todo.service;

import com.example.jpa_todo.dto.response.user.UserResponseDto;
import com.example.jpa_todo.entity.User;
import com.example.jpa_todo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public UserResponseDto signUp(String username, String email, String password) {
        User user = new User(username, email, password);
        User savedUser = userRepository.save(user);
        return UserResponseDto.toDto(savedUser);
    }

    public UserResponseDto login(String email, String password) {
        User user = userRepository.findByEmailAndPasswordOrThrow(email, password);
        return UserResponseDto.toDto(user);
    }
}
