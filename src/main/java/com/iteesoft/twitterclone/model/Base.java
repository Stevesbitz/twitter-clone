package com.iteesoft.twitterclone.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.Instant;
import static javax.persistence.GenerationType.IDENTITY;


@Getter
@Setter
@MappedSuperclass
public class Base {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private Instant createdAt = Instant.now();
    private Instant updatedAt;
}
