package com.teamgreen.makeplan.server.entity.document.comment;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "comment")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDocument {

    @Id
    private String id;

    private Long postId;
    private Integer userId;
    private String content;
    private LocalDateTime createdAt;
}
