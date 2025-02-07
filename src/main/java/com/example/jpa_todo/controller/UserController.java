package com.example.jpa_todo.controller;

import com.example.jpa_todo.common.SessionUtil;
import com.example.jpa_todo.dto.request.user.UpdateUserPasswordRequestDto;
import com.example.jpa_todo.dto.response.user.UserResponseDto;
import com.example.jpa_todo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User API", description = "유저(회원) 관련 API")
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "모든 유저 조회", description = "회원 등록되어 있는 모든 유저를 조회합니다.")
    public ResponseEntity<List<UserResponseDto>> findAll() {
        List<UserResponseDto> responseDto = userService.findAll();
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "특정 유저 조회", description = "회원 등록되어 있는 특정 유저를 조회합니다.")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id) {
        UserResponseDto responseDto = userService.findById(id);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "비밀번호 변경", description = "특정 회원의 비밀번호를 변경합니다.")
    public ResponseEntity<Map<String, String>> updatePassword(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserPasswordRequestDto requestDto,
            HttpServletRequest request
    ) {
        UserResponseDto sessionUser = SessionUtil.getSessionUser(request);
        userService.updatePassword(id, sessionUser.getId(), requestDto.getOldPassword(), requestDto.getNewPassword());
        return SessionUtil.expireSessionWithMessage(request, "세션이 만료되었습니다. 다시 로그인해주세요.");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "회원 삭제(회원 탈퇴)", description = "특정 회원의 정보를 삭제합니다.")
    public ResponseEntity<Map<String, String>> delete(
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        UserResponseDto sessionUser = SessionUtil.getSessionUser(request);
        userService.delete(id, sessionUser.getId());
        return SessionUtil.expireSessionWithMessage(request, "회원 탈퇴가 정상적으로 처리되었습니다.");
    }
}
