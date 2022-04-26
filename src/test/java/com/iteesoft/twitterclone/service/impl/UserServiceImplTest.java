package com.iteesoft.twitterclone.service.impl;

import com.iteesoft.twitterclone.dto.UserDto;
import com.iteesoft.twitterclone.model.User;
import com.iteesoft.twitterclone.repository.FollowRepository;
import com.iteesoft.twitterclone.repository.LikeRepository;
import com.iteesoft.twitterclone.repository.PostRepository;
import com.iteesoft.twitterclone.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private  UserRepository userRepo;
    @Mock
    private  PostRepository postRepo;
    @Mock
    private  LikeRepository likeRepo;
    @Mock
    private  FollowRepository followRepo;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldSaveUserSuccessfully() {
        final UserDto user = new UserDto("james", "jjm", "jjm@aol.com", "p1234");
        given(userRepo.findByEmail(user.getEmail())).willReturn(Optional.empty());
        given(userRepo.save(any(User.class))).willAnswer(invocation -> invocation.getArgument(0));

        User createdUser = userService.create(user);
        assertThat(createdUser).isNotNull();
        verify(userRepo).save(any(User.class));
    }

    @Test
    void testCreate() {
    }

    @Test
    void viewUserPosts() {
    }

    @Test
    void follow() {
    }

    @Test
    void unFollow() {
    }

    @Test
    void viewFollowers() {
    }

    @Test
    void viewFollowing() {
    }

    @Test
    void viewTimeline() {
    }

    @Test
    void getUsers() {
    }

    @Test
    void likePost() {
    }

    @Test
    void unlikePost() {
    }
}