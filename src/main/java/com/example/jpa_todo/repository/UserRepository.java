package com.example.jpa_todo.repository;

import com.example.jpa_todo.entity.User;
import com.example.jpa_todo.exception.ApplicationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailAndPassword(String email, String password);

    default User findByIdOrElseThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new ApplicationException(HttpStatus.NOT_FOUND, "해당 아이디의 유저가 없습니다 id = " + id));
    }

    default User findByEmailAndPasswordOrThrow(String email, String password) {
        return findByEmailAndPassword(email, password)
                .orElseThrow(() -> new ApplicationException(HttpStatus.UNAUTHORIZED, "이메일 혹은 패스워드가 일치하지 않습니다."));
    }
}
