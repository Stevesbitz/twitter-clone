package com.iteesoft.twitterclone.repository;

import com.iteesoft.twitterclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}
