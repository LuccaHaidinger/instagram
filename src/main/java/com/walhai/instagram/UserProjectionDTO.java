package com.walhai.instagram;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserProjectionDTO {
    private String username;
    private String email;
    private LocalDate createdAt;

    public UserProjectionDTO(String username, String email, LocalDate createdAt) {
        this.username = username;
        this.email = email;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "UserProjectionDTO{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
