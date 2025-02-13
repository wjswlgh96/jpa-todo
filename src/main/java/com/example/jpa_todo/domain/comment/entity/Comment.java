package com.example.jpa_todo.domain.comment.entity;

import com.example.jpa_todo.domain.user.entity.User;
import com.example.jpa_todo.common.entity.BaseEntity;
import com.example.jpa_todo.domain.schedule.entity.Schedule;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "comment")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String contents;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public Comment() {}

    public Comment(String contents) {
        this.contents = contents;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public void updateContents(String contents) {
        this.contents = contents;
    }
}
