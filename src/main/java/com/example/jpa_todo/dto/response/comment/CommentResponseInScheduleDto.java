package com.example.jpa_todo.dto.response.comment;

import com.example.jpa_todo.entity.Comment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CommentResponseInScheduleDto {

    private final Long id;

    private final Long userId;

    private final String username;

    private final String contents;

    private final LocalDateTime createdAt;

    private final LocalDateTime modifiedAt;

    public static CommentResponseInScheduleDto toDto(Comment comment) {
        return new CommentResponseInScheduleDto(
                comment.getId(),
                comment.getUser().getId(),
                comment.getUser().getUsername(),
                comment.getContents(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }
}
