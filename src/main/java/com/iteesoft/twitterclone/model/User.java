package com.iteesoft.twitterclone.model;

import lombok.*;
import javax.persistence.*;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User extends Base{

    private String name;
    private String username;
    private String email;
    private String password;
}