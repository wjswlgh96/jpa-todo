package com.example.jpa_todo.domain.user.service;

import com.example.jpa_todo.config.PasswordEncoder;
import com.example.jpa_todo.domain.user.dto.response.UserResponseDto;
import com.example.jpa_todo.domain.user.entity.User;
import com.example.jpa_todo.common.exception.ApplicationException;
import com.example.jpa_todo.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<UserResponseDto> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserResponseDto::toDto)
                .toList();
    }

    public UserResponseDto getUserById(Long id) {
        User findUser = findByIdOrElseThrow(id);
        return UserResponseDto.toDto(findUser);
    }

    @Transactional
    public void updatePassword(Long id, Long sessionId, String oldPassword, String newPassword) {
        User findUser = findByIdOrElseThrow(id);

        if (!findUser.getId().equals(sessionId)) {
            throw new ApplicationException(HttpStatus.FORBIDDEN, "자신의 비밀번호만 변경할 수 있습니다.");
        }

        if (!passwordEncoder.matches(oldPassword, findUser.getPassword())) {
            throw new ApplicationException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        if (passwordEncoder.matches(newPassword, findUser.getPassword())) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "새 비밀번호는 기존 비밀번호와 달라야 합니다.");
        }

        findUser.updatePassword(passwordEncoder.encode(newPassword));
    }

    @Transactional
    public void deleteUser(Long id, Long sessionId) {
        User findUser = findByIdOrElseThrow(id);

        if (!findUser.getId().equals(sessionId)) {
            throw new ApplicationException(HttpStatus.FORBIDDEN, "자기 자신만 삭제할 수 있습니다.");
        }

        userRepository.delete(findUser);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User findByIdOrElseThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(HttpStatus.NOT_FOUND, "해당 아이디의 유저가 없습니다 id = " + id));
    }

    public User findByEmailOrThrow(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ApplicationException(HttpStatus.UNAUTHORIZED, "해당 이메일의 유저가 존재하지 않습니다 email = " + email));
    }
}
