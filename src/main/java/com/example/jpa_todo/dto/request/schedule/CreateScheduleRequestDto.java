package com.example.jpa_todo.dto.request.schedule;

import lombok.Getter;

@Getter
public class CreateScheduleRequestDto {

    private final String title;

    private final String contents;

    public CreateScheduleRequestDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
