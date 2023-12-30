package com.ead.authuser.controllers;


import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class AuthenticatorController {

    private final UserService userService;


    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@RequestBody
                                                   //@JsonView faz o filtro dos campos do DTO utilizando as interfaces
                                                    //criadas em UserDto
                                                   @JsonView(UserDto.UserView.RegistrationPost.class) UserDto userDto) {

        if(userService.existsByUsername(userDto.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Username is Already Taken!");
        }

        if(userService.existsByEmail(userDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Email is Already Taken!");
        }


        var userModel = new UserModel();

        BeanUtils.copyProperties(userDto, userModel);
        userModel.setUserStatus(UserStatus.ACTIVE);
        userModel.setUserType(UserType.STUDENT);
        userModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        userService.save(userModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(userModel);

    }


}
