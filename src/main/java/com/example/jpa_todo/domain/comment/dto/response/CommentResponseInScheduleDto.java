package com.example.jpa_todo.domain.comment.dto.response;

import com.example.jpa_todo.domain.comment.entity.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CommentResponseInScheduleDto {

    @Schema(example = "1")
    private final Long id;

    @Schema(example = "1")
    private final Long userId;

    @Schema(example = "홍길동")
    private final String username;

    @Schema(example = "댓글 내용 입니다!!")
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
