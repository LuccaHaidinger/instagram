package com.walhai.instagram.mongo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "users")
public class Users {

    @Id
    private String _id;
    private String username;
    private String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate created_at;

    public Users() {}

    public Users(String username, String email, LocalDate created_at) {
        this.username = username;
        this.email = email;
        this.created_at = created_at;
    }
}
