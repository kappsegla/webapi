package org.fungover.webapi.controllers;

import org.fungover.webapi.entities.User;
import org.fungover.webapi.repositories.UserEntityRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserEntityRepository userEntityRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserEntityRepository userEntityRepository, PasswordEncoder passwordEncoder) {
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


}
