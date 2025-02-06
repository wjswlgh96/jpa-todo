package com.example.jpa_todo.dto.request.schedule;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateScheduleRequestDto {

    @NotBlank(message = "제목은 필수값입니다.")
    @Size(max = 50, message = "제목은 50글자를 넘길 수 없습니다.")
    private final String title;

    @NotBlank(message = "내용은 필수값입니다.")
    @Size(max = 500, message = "내용은 500글자를 넘길 수 없습니다.")
    private final String contents;

    public CreateScheduleRequestDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
