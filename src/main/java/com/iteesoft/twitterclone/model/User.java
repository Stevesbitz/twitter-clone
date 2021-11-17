package com.iteesoft.twitterclone.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User extends Base{

    private String name;
    private String username;
    private String email;
    private String password;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    private List<User> followers = new ArrayList<>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    private List<User> following = new ArrayList<>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    private List<Post> likedPosts = new ArrayList<>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Post> posts = new ArrayList<>();
}