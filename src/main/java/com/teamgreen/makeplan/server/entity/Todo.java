package com.teamgreen.makeplan.server.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="todo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; //글번호

    @Column(name = "is_done", nullable = false, columnDefinition = "TINYINT(1) default 0")
    private boolean isDone; // todo 완료 / 미완료 기본값 미완료

    @Column(nullable = false)
    private String content; // todo 내용

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime targetDate; //언제까지 완료해야되는지

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST) // todo조회시 user도 함께 가져옴, todo저장시 user도 같이 저장
    @JoinColumn(name = "writer_id", nullable = false, referencedColumnName = "id")
    private User writer; //작성자


}
