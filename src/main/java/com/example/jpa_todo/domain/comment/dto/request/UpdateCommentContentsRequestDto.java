package com.example.jpa_todo.domain.comment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateCommentContentsRequestDto {

    @NotBlank(message = "댓글 내용은 필수입니다.")
    @Size(max = 100, message = "댓글은 100글자를 넘길 수 없습니다.")
    @Schema(description = "댓글 내용 입력 (필수, 최대 100자)", example = "수정된 댓글 내용입니다!!")

    private String contents;

}
