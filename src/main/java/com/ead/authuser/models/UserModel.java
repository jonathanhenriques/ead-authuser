package com.ead.authuser.models;


import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
//define a nao serializacao de parametros null no Json
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "EAD_TB_USERS")
public class UserModel extends RepresentationModel<UserModel> implements Serializable {

    private static final long serialVersionUID = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userId;
//    @Column define os campos na tabela do BD automaticamente
    @Column(nullable = false, unique = true, length = 50)
    private String username;
    @Column(nullable = false, unique = true, length = 50)
    private String email;
    @Column(nullable = false, unique = false, length = 255)
//    @JsonIgnore nao mostra o campo no Json
    @JsonIgnore
    private String password;
    @Column(nullable = false, unique = false, length = 150)
    private String fullName;
    @Column(nullable = false)
//    Salva o Enum como String no BD
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
    @Column(nullable = false)
//    Salva o Enum como String no BD
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @Column(length = 20)
    private String phoneNumber;
    @Column(length = 20)
    private String cpf;
    @Column
    private String imageUrl;
    @Column(nullable = false)
//    retorna a data no formato esperado
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime creationDate;
    @Column(nullable = false)
    //    retorna a data no formato esperado
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime lastUpdateDate;

}
