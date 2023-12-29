package com.ead.authuser.controllers;


import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserModel>> getAlUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @GetMapping("/{userId")
    public ResponseEntity<Object> getOneUser(@PathVariable(value = "userId") UUID userId) {

        Optional<UserModel> userModelOptional = userService.findById(userId);
//        return userService.findById(userId)
//                .orElseThrow(() -> new UserNaoEncontradoException(userId.toString()));
//        ou
//        return userModelOptional.<ResponseEntity<Object>>map(userModel -> ResponseEntity.status(HttpStatus.NOT_FOUND)
//        .body(userModel)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found"));
        if(!userModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userModelOptional.get());
        }
    }

    @DeleteMapping("/{userId")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "userId") UUID userId) {
        Optional<UserModel> userModelOptional = userService.findById(userId);
        if(!userModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        else {
            userService.delete(userModelOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body("User deleted success!");
        }

    }

}
