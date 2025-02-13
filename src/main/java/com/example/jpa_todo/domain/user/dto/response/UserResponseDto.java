package com.example.jpa_todo.domain.user.dto.response;

import com.example.jpa_todo.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class UserResponseDto {

    @Schema(example = "1")
    private final Long id;

    @Schema(example = "홍길동")
    private final String username;

    @Schema(example = "hong@example.com")
    private final String email;

    private final LocalDateTime createdAt;

    private final LocalDateTime modifiedAt;

    public static UserResponseDto toDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }
}
