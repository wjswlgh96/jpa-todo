package com.example.jpa_todo.common.utils;

import com.example.jpa_todo.common.Const;
import com.example.jpa_todo.domain.user.dto.response.UserResponseDto;
import com.example.jpa_todo.common.exception.ApplicationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public final class SessionUtil {    // final: 상속 방지

    private SessionUtil() {}    // 인스턴스화 방지

    // 현재 로그인된 유저의 세션 가져오는 메서드
    public static UserResponseDto getSessionUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new ApplicationException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }

        Object sessionUserObj = session.getAttribute(Const.LOGIN_USER);
        if (!(sessionUserObj instanceof UserResponseDto sessionUser)) {
            throw new ApplicationException(HttpStatus.UNAUTHORIZED, "세션이 만료되었습니다. 다시 로그인 해주세요.");
        }

        return sessionUser;
    }

    // 세션 만료 시키는 메서드
    public static ResponseEntity<Map<String, String>> expireSessionWithMessage(HttpServletRequest request, String message) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        Map<String, String> response = new HashMap<>();
        response.put("message", message);

        return ResponseEntity.ok(response);
    }
}
