package com.iteesoft.twitterclone.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.time.Instant;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Post extends Base{

    private String message;
    private Integer likes = 0;
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;
}
