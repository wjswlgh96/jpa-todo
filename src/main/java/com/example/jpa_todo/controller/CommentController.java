package com.example.jpa_todo.controller;

import com.example.jpa_todo.common.Const;
import com.example.jpa_todo.dto.request.comment.CreateCommentRequestDto;
import com.example.jpa_todo.dto.request.comment.UpdateCommentContentsRequestDto;
import com.example.jpa_todo.dto.response.comment.CommentResponseDto;
import com.example.jpa_todo.dto.response.user.UserResponseDto;
import com.example.jpa_todo.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> save(
            @Valid @RequestBody CreateCommentRequestDto requestDto,
            HttpServletRequest request
    ) {
        HttpSession session = request.getSession(false);
        UserResponseDto sessionUser = (UserResponseDto) session.getAttribute(Const.LOGIN_USER);
        CommentResponseDto responseDto = commentService.save(sessionUser.getId(), requestDto.getScheduleId(), requestDto.getContents());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> findAll() {
        List<CommentResponseDto> responseDto = commentService.findAll();
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseDto> findById(@PathVariable Long id) {
        CommentResponseDto responseDto = commentService.findById(id);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateContents(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCommentContentsRequestDto requestDto,
            HttpServletRequest request
    ) {
        HttpSession session = request.getSession();
        UserResponseDto sessionUser = (UserResponseDto) session.getAttribute(Const.LOGIN_USER);

        commentService.updateContents(sessionUser.getId(), id, requestDto.getContents());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        HttpSession session = request.getSession();
        UserResponseDto sessionUser = (UserResponseDto) session.getAttribute(Const.LOGIN_USER);

        commentService.delete(sessionUser.getId(), id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
