package com.example.jpa_todo.dto.request.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateCommentRequestDto {

    @NotNull(message = "할일 게시물의 id는 필수입니다.")
    @Schema(description = "할일 게시물 id (필수)", example = "1")
    private final Long scheduleId;

    @NotBlank(message = "댓글 내용은 필수입니다.")
    @Size(max = 100, message = "댓글은 100글자를 넘길 수 없습니다.")
    @Schema(description = "댓글 내용 입력 (필수, 최대 100자)", example = "댓글 내용 입니다!!")
    private final String contents;

    public CreateCommentRequestDto(Long scheduleId, String contents) {
        this.scheduleId = scheduleId;
        this.contents = contents;
    }
}
