package com.example.jpa_todo.domain.schedule.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateScheduleRequestDto {

    @NotBlank(message = "제목은 필수값입니다.")
    @Size(max = 50, message = "제목은 50글자를 넘길 수 없습니다.")
    @Schema(description = "할일의 제목 (필수, 최대 50자)", example = "제목")
    private String title;

    @NotBlank(message = "내용은 필수값입니다.")
    @Size(max = 500, message = "내용은 500글자를 넘길 수 없습니다.")
    @Schema(description = "할일의 내용 (필수, 최대 200자)", example = "내용입니다.")
    private String contents;

}
