package com.walhai.instagram.mongo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "follows")
public class Follows {

    @Id
    private String _id;
    private Long followee_id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate since;

    public Follows() {}

    public Follows(Long followee_id, LocalDate since) {
        this.followee_id = followee_id;
        this.since = since;
    }
}
