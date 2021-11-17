package com.iteesoft.twitterclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private String name;
    private String username;
    private String email;
    private String password;
}
