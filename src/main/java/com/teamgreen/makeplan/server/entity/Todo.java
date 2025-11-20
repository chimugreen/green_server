package com.teamgreen.makeplan.server.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name="todo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; //글번호

    @Column(name = "is_done", nullable = false, columnDefinition = "TINYINT(1) default 0")
    private boolean isDone; // todo 완료 / 미완료 기본값 미완료

    @Column(nullable = false)
    private String content; // todo 내용

    @CreatedDate
    private LocalDateTime createDate; //todo 생성날짜

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime targetDate; //언제까지 완료해야되는지

    @ManyToOne(fetch = FetchType.LAZY) //유저 삭제시 todo도 삭제
    @JoinColumn(name = "writer_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User writer;


}
