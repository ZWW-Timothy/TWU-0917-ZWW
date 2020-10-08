package com.thoughtworks.rslist.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(name = "vote")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoteEntity {

    @Id
    @GeneratedValue
    private Integer voteId;

    private Integer voteNum;

    // private Timestamp voteTime;
    private LocalDateTime voteTime;

    @ManyToOne
    @JoinColumn(name = "user_userId")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "rs_event_rsEventId")
    private RsEventEntity rsEventEntity;
}
