package com.example.jpa_todo.service;

import com.example.jpa_todo.dto.response.comment.CommentResponseDto;
import com.example.jpa_todo.entity.Comment;
import com.example.jpa_todo.entity.Schedule;
import com.example.jpa_todo.entity.User;
import com.example.jpa_todo.exception.ApplicationException;
import com.example.jpa_todo.repository.CommentRepository;
import com.example.jpa_todo.repository.ScheduleRepository;
import com.example.jpa_todo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    public CommentResponseDto save(Long sessionId, Long scheduleId, String contents) {
        User findUser = userRepository.findByIdOrElseThrow(sessionId);
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        Comment comment = new Comment(contents);
        findUser.addComment(comment);
        findSchedule.addComment(comment);
        commentRepository.save(comment);

        return CommentResponseDto.toDto(comment);
    }

    public List<CommentResponseDto> findAll() {
        return commentRepository.findAll()
                .stream()
                .map(CommentResponseDto::toDto)
                .toList();
    }

    public CommentResponseDto findById(Long id) {
        Comment findComment = commentRepository.findByIdOrElseThrow(id);
        return CommentResponseDto.toDto(findComment);
    }

    @Transactional
    public void updateContents(Long sessionId, Long id, String contents) {
        Comment findComment = commentRepository.findByIdOrElseThrow(id);

        if (!findComment.getUser().getId().equals(sessionId)) {
            throw new ApplicationException(HttpStatus.FORBIDDEN, "자신이 작성한 댓글만 수정이 가능합니다.");
        }

        findComment.updateContents(contents);
    }

    public void delete(Long sessionId, Long id) {
        Comment findComment = commentRepository.findByIdOrElseThrow(id);

        if (!findComment.getUser().getId().equals(sessionId)) {
            throw new ApplicationException(HttpStatus.FORBIDDEN, "자신이 작성한 댓글만 삭제가 가능합니다");
        }

        commentRepository.delete(findComment);
    }
}
