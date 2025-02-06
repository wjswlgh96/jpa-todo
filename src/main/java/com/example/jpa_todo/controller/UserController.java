package com.example.jpa_todo.controller;

import com.example.jpa_todo.common.Const;
import com.example.jpa_todo.dto.request.user.UpdatePasswordRequestDto;
import com.example.jpa_todo.dto.response.user.UserResponseDto;
import com.example.jpa_todo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAll() {
        List<UserResponseDto> responseDto = userService.findAll();
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id) {
        UserResponseDto responseDto = userService.findById(id);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePasswordRequestDto requestDto,
            HttpServletRequest request
    ) {
        HttpSession session = request.getSession(false);
        UserResponseDto sessionUser = (UserResponseDto) session.getAttribute(Const.LOGIN_USER);

        userService.updatePassword(id, sessionUser.getId(), requestDto.getOldPassword(), requestDto.getNewPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        HttpSession session = request.getSession(false);
        UserResponseDto sessionUser = (UserResponseDto) session.getAttribute(Const.LOGIN_USER);

        userService.delete(id, sessionUser.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
