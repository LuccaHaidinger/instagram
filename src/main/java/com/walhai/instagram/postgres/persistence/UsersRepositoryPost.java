package com.walhai.instagram.postgres.persistence;

import com.walhai.instagram.UserProjectionDTO;
import com.walhai.instagram.postgres.model.Users;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface UsersRepositoryPost extends JpaRepository<Users, Long> {

    @Query("SELECT u FROM Users u WHERE u.username LIKE %:username%")
    List<Users> findByUsername(@Param("username") String username);

    @Query("SELECT new com.walhai.instagram.UserProjectionDTO(u.username, u.email, u.created_at) " +
            "FROM Users u WHERE u.username LIKE %:username%")
    List<UserProjectionDTO> findByUsernameWithProjection(@Param("username") String username);

    @Query("SELECT new com.walhai.instagram.UserProjectionDTO(u.username, u.email, u.created_at) " +
            "FROM Users u WHERE u.username LIKE %:username%")
    List<UserProjectionDTO> findByUsernameWithProjectionAndSorting(@Param("username") String username, Sort sort);
}