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
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
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

    @Column(nullable = false)
    private LocalDateTime schedule; //어느날짜의 todo인지

    @ManyToOne(fetch = FetchType.LAZY) //지연로딩(LAZY) 필요할때만 User데이터 조회 @ManToOne은 무조건 LAZY
    @JoinColumn(name = "writer_id", nullable = false)//유저 삭제시 todo도 삭제
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User writer;


}
