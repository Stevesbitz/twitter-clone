package com.iteesoft.twitterclone.repository;

import com.iteesoft.twitterclone.model.Follow;
import com.iteesoft.twitterclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFollowerId(Long followerId);

    Optional<Follow> findByFollowerAndFollowed(User follower, User followed);

    List<Follow> findAllByFollowerId(Long followerId);
}
