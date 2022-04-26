package com.iteesoft.twitterclone.repository;

import com.iteesoft.twitterclone.model.Like;
import com.iteesoft.twitterclone.model.Post;
import com.iteesoft.twitterclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndPost(User user, Post post);
}
