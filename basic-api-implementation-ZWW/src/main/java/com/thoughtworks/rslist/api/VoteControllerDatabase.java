package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.dto.VoteDto;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.entity.VoteEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.validation.Valid;

@RestController
public class VoteControllerDatabase {
    private final UserRepository userRepository;
    private final RsEventRepository rsEventRepository;
    private final VoteRepository voteRepository;

    public VoteControllerDatabase(UserRepository userRepository, RsEventRepository rsEventRepository, VoteRepository voteRepository) {
        this.userRepository = userRepository;
        this.rsEventRepository = rsEventRepository;
        this.voteRepository = voteRepository;
    }

    @PostMapping("/rsDatabase/vote/{rsEventId}")
    public ResponseEntity vote(@PathVariable Integer rsEventId, @RequestBody VoteDto voteDto) {

        if (userRepository.findById(voteDto.getUserId()).get().getUserVoteNum() >= voteDto.getVoteNum()) {

            VoteEntity voteEntity = VoteEntity.builder()
                    .rsEventEntity(rsEventRepository.findById(rsEventId).get())
                    .userEntity(userRepository.findById(voteDto.getUserId()).get())
                    .voteNum(voteDto.getVoteNum())
                    .voteTime(voteDto.getVoteTime())
                    .build();

            RsEventEntity rsEventEntity = rsEventRepository.findById(rsEventId).get();
            rsEventEntity.setRsEventVoteNum(voteDto.getVoteNum() + rsEventEntity.getRsEventVoteNum());
            rsEventRepository.save(rsEventEntity);
            voteRepository.save(voteEntity);
        } else {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}
