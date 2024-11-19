package com.walhai.instagram.postgres.persistence;

import com.walhai.instagram.postgres.model.Follows;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowsRepositoryPost extends JpaRepository<Follows, Long> {

}
