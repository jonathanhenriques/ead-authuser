package com.ead.authuser.controllers;


import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
import com.ead.authuser.specifications.SpecificationTemplate;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserModel>> getAlUsers(SpecificationTemplate.UserSpec spec,
                                                      @PageableDefault(page= 0, size = 10, sort = "userId",
                                                        direction = Sort.Direction.ASC)
                                                        Pageable pageable) {
        Page<UserModel> userModelPage = userService.findAll(spec, pageable);

       //adicionando spring HATEOS **************
        if(!userModelPage.isEmpty()) {
            for(UserModel user : userModelPage.toList()) {
                user.add(linkTo(methodOn(UserController.class).getOneUser(user.getUserId())).withSelfRel());
            }
        }
        //adicionando spring HATEOS *************
        return ResponseEntity.status(HttpStatus.OK).body(userModelPage);
    }

    @GetMapping("/{userId}")
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

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "userId") UUID userId) {
        Optional<UserModel> userModelOptional = userService.findById(userId);
        if(!userModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        else {
            userService.delete(userModelOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully!");
        }

    }


    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@Validated(UserDto.UserView.UserPut.class) //passamos a classe do Dto,
                                                 // a interface de filtro do dto e interface de validacao
                                            @RequestBody
                                             @PathVariable(value = "userId") UUID userId,
                                             //@JsonView faz o filtro dos campos do DTO utilizando as interfaces
                                             //criadas em UserDto
                                             @JsonView(UserDto.UserView.UserPut.class) UserDto userDto) {
        Optional<UserModel> userModelOptional = userService.findById(userId);
        if(!userModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        else {
            var userModel = userModelOptional.get();
            userModel.setFullName(userDto.getFullName());
            userModel.setPhoneNumber(userDto.getPhoneNumber());
            userModel.setCpf(userDto.getCpf());
            userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

            userService.save(userModel);

            return ResponseEntity.status(HttpStatus.OK).body(userModel);
        }

    }


    @PutMapping("/{userId}/password")
    public ResponseEntity<Object> updatePassword(@Validated(UserDto.UserView.PasswordPut.class) //passamos a classe do Dto,
                                                     // a interface de filtro do dto e interface de validacao
                                                @RequestBody
                                                 @PathVariable(value = "userId") UUID userId,
                                             //@JsonView faz o filtro dos campos do DTO utilizando as interfaces
                                             //criadas em UserDto
                                             @JsonView(UserDto.UserView.PasswordPut.class) UserDto userDto) {
        Optional<UserModel> userModelOptional = userService.findById(userId);
        if(!userModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } if(!userModelOptional.get().getPassword().equals(userDto.getOldPassword())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Mismatched old password!");
        }
        else {
            var userModel = userModelOptional.get();
            userModel.setPassword(userDto.getPassword());
            userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

            userService.save(userModel);

            return ResponseEntity.status(HttpStatus.OK).body("Password updated sucessfully.");
        }

    }



    @PutMapping("/{userId}/image")
    public ResponseEntity<Object> updateImage(@Validated(UserDto.UserView.ImagePut.class) //passamos a classe do Dto,
                                                  // a interface de filtro do dto e interface de validacao
                                                @RequestBody
                                                @PathVariable(value = "userId") UUID userId,
                                                 //@JsonView faz o filtro dos campos do DTO utilizando as interfaces
                                                 //criadas em UserDto
                                                 @JsonView(UserDto.UserView.ImagePut.class) UserDto userDto) {
        Optional<UserModel> userModelOptional = userService.findById(userId);
        if(!userModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        else {
            var userModel = userModelOptional.get();
            userModel.setImageUrl(userDto.getImageUrl());
            userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

            userService.save(userModel);

            return ResponseEntity.status(HttpStatus.OK).body(userModel);
        }

    }

}
