package com.example.jpa_todo.common;

import com.example.jpa_todo.dto.response.user.UserResponseDto;
import com.example.jpa_todo.exception.ApplicationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;

public final class SessionUtil {    // final: 상속 방지

    private SessionUtil() {}    // 인스턴스화 방지

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
}
