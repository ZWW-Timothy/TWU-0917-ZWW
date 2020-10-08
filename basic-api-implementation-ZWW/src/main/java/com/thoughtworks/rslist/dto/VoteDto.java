package com.thoughtworks.rslist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteDto {

    @NotNull
    private Integer userId;

    @NotNull
    private Integer rsEventId;

    @NotNull
    private Integer voteNum;

    // private Timestamp voteTime;
    private LocalDateTime voteTime;
}
