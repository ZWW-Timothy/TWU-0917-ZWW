package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserControllerDatabase {

    private final UserRepository userRepository;
    private final RsEventRepository rsEventRepository;

    public UserControllerDatabase(UserRepository userRepository, RsEventRepository rsEventRepository) {
        this.userRepository = userRepository;
        this.rsEventRepository = rsEventRepository;
    }

    @PostMapping("/userDatabase/register")
    public void register(@RequestBody @Valid UserEntity user) {
        UserEntity userEntity = UserEntity.builder()
                .userName(user.getUserName())
                .userGender(user.getUserGender())
                .userAge(user.getUserAge())
                .userEmail(user.getUserEmail())
                .userPhoneNum(user.getUserPhoneNum())
                .userVoteNum(user.getUserVoteNum())
                .build();
        userRepository.save(userEntity);
    }

    @GetMapping("/userDatabase/{id}")
    public ResponseEntity getUser(@PathVariable int id) {
        userRepository.findById(id);
        return ResponseEntity.ok().build();
    }

    @ResponseStatus(code = HttpStatus.OK)
    @DeleteMapping("/userDatabase/delete/{id}")
    @Transactional
    public ResponseEntity deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
