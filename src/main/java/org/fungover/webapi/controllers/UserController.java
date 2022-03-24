package org.fungover.webapi.controllers;

import org.fungover.webapi.entities.User;
import org.fungover.webapi.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.StreamSupport;

@RestController
public class UserController {

    private final UserRepository userEntityRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userEntityRepository, PasswordEncoder passwordEncoder) {
        this.userEntityRepository = userEntityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/users")
    public ResponseEntity<Void> createUser(@RequestBody User userEntity) {
        if( userEntityRepository.findByName(userEntity.getName()) != null)
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntityRepository.save(userEntity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public List<String> userNames(){
       return userEntityRepository.getNameOnly();
    }
}
