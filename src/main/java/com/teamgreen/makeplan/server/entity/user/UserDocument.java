package com.teamgreen.makeplan.server.entity.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDocument {
    @Id
    private String mongoId; // Mongo의 내부 id
    private Integer id; //Rdb의 userID
    private List<Integer> followers = new ArrayList<>();
    private List<Integer> following = new ArrayList<>();
}
