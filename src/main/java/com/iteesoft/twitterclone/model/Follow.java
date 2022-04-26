package com.iteesoft.twitterclone.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "follow_table")
public class Follow extends Base{

    @OneToOne
    private User follower;

    @OneToOne
    private User followed;
}
