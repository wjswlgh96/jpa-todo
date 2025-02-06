package com.example.jpa_todo.service;

import com.example.jpa_todo.dto.response.user.UserResponseDto;
import com.example.jpa_todo.entity.User;
import com.example.jpa_todo.exception.ApplicationException;
import com.example.jpa_todo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponseDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserResponseDto::toDto)
                .toList();
    }

    public UserResponseDto findById(Long id) {
        User findUser = userRepository.findByIdOrElseThrow(id);
        return UserResponseDto.toDto(findUser);
    }

    @Transactional
    public void updatePassword(Long id, Long sessionId, String oldPassword, String newPassword) {
        User findUser = userRepository.findByIdOrElseThrow(id);

        if (!findUser.getId().equals(sessionId)) {
            throw new ApplicationException(HttpStatus.FORBIDDEN, "자신의 비밀번호만 변경할 수 있습니다.");
        }

        if (!findUser.getPassword().equals(oldPassword)) {
            throw new ApplicationException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        findUser.updatePassword(newPassword);
    }

    public void delete(Long id, Long sessionId) {
        User findUser = userRepository.findByIdOrElseThrow(id);

        if (!findUser.getId().equals(sessionId)) {
            throw new ApplicationException(HttpStatus.FORBIDDEN, "자기 자신만 삭제할 수 있습니다.");
        }

        userRepository.delete(findUser);
    }
}
