package com.iteesoft.twitterclone.service.impl;

import com.iteesoft.twitterclone.dto.UserDto;
import com.iteesoft.twitterclone.exceptions.AlreadyExistsException;
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

import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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
    void shouldThrowExceptionIfUserAlreadyExists() {
        final UserDto user = new UserDto("jason", "jboy", "jason@aol.com", "p1234");
        final User existingUser = User.builder().name("jason").email("jason@aol.com").build();
        given(userRepo.findByEmail(user.getEmail())).willReturn(Optional.of(existingUser));
        assertThrows(AlreadyExistsException.class, () -> userService.create(user));
        verify(userRepo, never()).save(any(User.class));
    }

    @Test
    void shouldUpdateUserSuccessfully() {
//        final User user = User.builder().name("james").email("james@aol.com").build();
//        given(userRepo.save(user)).willReturn(user);
//        User updatedUser = userService.updatedUser(user);
//        assertThat(updatedUser).isNotNull();
//        verify(userRepo).save(user);
    }

    @Test
    void shouldReturnAllUsers() {
        List<User> users = List.of(
                User.builder().name("obaloluwa").build(),
                User.builder().name("marvellous").build(),
                User.builder().name("ayomide").build()
        );
        given(userRepo.findAll()).willReturn(users);
        var expected = userService.getUsers();
        assertEquals(expected, users);
    }

    @Test
    void shouldFindUserById() {
        final long id = 1L;
        final User user = User.builder().name("obaloluwa").build();
        given(userRepo.findById(id)).willReturn(Optional.of(user));
        final User expected = userService.getUserById(id);
        assertThat(expected).isNotNull();
    }

    @Test
    void shouldDeleteUser() {
        final long id = 1L;
        final User user = User.builder().name("obaloluwa").build();
        given(userRepo.findById(id)).willReturn(Optional.of(user));
        userService.deleteUser(id);
        userService.deleteUser(id);

        verify(userRepo, times(2)).deleteById(id);
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