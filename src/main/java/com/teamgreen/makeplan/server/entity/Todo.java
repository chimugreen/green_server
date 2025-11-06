package com.teamgreen.makeplan.server.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name="todo")
@Data
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; //글번호

    @Column(name = "is_done", nullable = false, columnDefinition = "TINYINT(1) default 0")
    private boolean isDone; // todo 완료 / 미완료 기본값 미완료

    @Column(nullable = false)
    private String content; // todo 내용

    private LocalDateTime targetDate; //언제까지 완료해야되는지

    @ManyToOne(fetch = FetchType.EAGER) //
    @JoinColumn(name = "writer_id", nullable = false)
    private User writer; //작성자

}
