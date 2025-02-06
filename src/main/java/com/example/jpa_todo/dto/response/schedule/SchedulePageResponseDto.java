package com.example.jpa_todo.dto.response.schedule;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class SchedulePageResponseDto<T> {

    private final List<T> content;
    private final int size;
    private final int number;
    private final long totalElements;
    private final int totalPages;

    public static <T> SchedulePageResponseDto<T> toDto(Page<T> page) {
        return new SchedulePageResponseDto<>(
                page.getContent(),
                page.getSize(),
                page.getNumber() + 1,
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
}
