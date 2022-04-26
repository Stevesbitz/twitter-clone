package com.iteesoft.twitterclone.service;

import com.iteesoft.twitterclone.dto.UserDto;
import com.iteesoft.twitterclone.model.Post;
import com.iteesoft.twitterclone.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User create(UserDto userInfo);
    Post create(String message, Long id);
    List<Post> viewUserPosts(Long id);
    void follow(Long followerId, Long userId);
    void unFollow(Long followerId, Long userId);
    List<User> viewFollowers(Long id);

    List<User> viewFollowing(Long id);

    List<Post> viewTimeline(Long id);
    List<User> getUsers();

    void likePost(Long userId, Long postId);

    void unlikePost(Long userId, Long postId);

    User updatedUser(UserDto user, long id);

    User getUserById(long id);

    void deleteUser(long id);
}
