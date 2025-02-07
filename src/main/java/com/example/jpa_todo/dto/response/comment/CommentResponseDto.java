package com.example.jpa_todo.dto.response.comment;

import com.example.jpa_todo.entity.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CommentResponseDto {

    @Schema(example = "1")
    private final Long id;

    @Schema(example = "1")
    private final Long userId;

    @Schema(example = "1")
    private final Long scheduleId;

    @Schema(example = "댓글 내용 입니다!!")
    private final String contents;

    private final LocalDateTime createdAt;

    private final LocalDateTime modifiedAt;

    public static CommentResponseDto toDto(Comment comment) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getUser().getId(),
                comment.getSchedule().getId(),
                comment.getContents(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }
}
