package com.ead.authuser.dtos;

import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
//define a nao serializacao de parametros null no Json
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userId;
//    @Column define os campos na tabela do BD automaticamente
//    @Column(nullable = false, unique = true, length = 50)
    private String username;
//    @Column(nullable = false, unique = true, length = 50)
    private String email;
//    @Column(nullable = false, unique = false, length = 255)
//    @JsonIgnore nao mostra o campo no Json
//    @JsonIgnore
    private String password;
//    @JsonIgnore
    private String oldPassword;
//    @Column(nullable = false, unique = false, length = 150)
    private String fullName;
//    @Column(length = 20)
    private String phoneNumber;
//    @Column(length = 20)
    private String cpf;
//    @Column
    private String imageUrl;
}
