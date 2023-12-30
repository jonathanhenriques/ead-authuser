package com.ead.authuser.dtos;

import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
//define a nao serializacao de parametros null no Json
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {


    //interface criada para usar com o jsonView
    //viabiliza o uso de apenas um DTO para tudo
    public interface UserView {


        //crud basico do usuario
        public static interface RegistrationPost{}
        //atualiza os dados basicos do usuario
        public static interface UserPut {}
        //atualizar a senha do usuario
        public static interface PasswordPut {}
        //atualizar a imagem do usuario
        public static interface ImagePut {}

    }




    private UUID userId;
    //vincula a variavel username a interface RegistrationPost
    //username so pode ser alterado no momento do cadastro
    @JsonView(UserView.RegistrationPost.class)
    @NotBlank(groups = UserView.RegistrationPost.class) //define qual grupo deve ser validado
    @Size(min = 4, max = 50)
    private String username;
    //vincula a variavel email a interface RegistrationPost
    //email so pode ser alterado no momento do cadastro
    @JsonView(UserView.RegistrationPost.class)
    @NotBlank(groups = UserView.RegistrationPost.class) //define qual grupo deve ser validado
    @Email
    private String email;
    @JsonView({UserView.RegistrationPost.class, UserView.PasswordPut.class})
    //vincula a variavel password a interface RegistrationPost
    //password  pode ser registrado no momento do cadastro e na atualizacao posterior
    @NotBlank(groups = {UserView.RegistrationPost.class, UserView.PasswordPut.class}) //define qual grupo deve ser validado
    @Size(min = 6, max = 20)
    private String password;
    @JsonView({UserView.PasswordPut.class})
    //vincula a variavel oldPassword a interface RegistrationPost
    //oldPassword so pode ser alterado apos o cadastro
    @NotBlank(groups = UserView.PasswordPut.class) //define qual grupo deve ser validado
    @Size(min = 6, max = 20)
    private String oldPassword;
    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    //vincula a variavel fullName a interface RegistrationPost
    //fullName  pode ser registrado no momento do cadastro e na atualizacao posterior
    private String fullName;
    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    //vincula a variavel phoneNumber a interface RegistrationPost
    //phoneNumber  pode ser registrado no momento do cadastro e na atualizacao posterior
    private String phoneNumber;
    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    //vincula a variavel cpf a interface RegistrationPost
    //cpf  pode ser registrado no momento do cadastro e na atualizacao posterior
    private String cpf;
    @JsonView(UserView.ImagePut.class)
    //vincula a variavel imageUrl a interface RegistrationPost
    //imageUrl  pode ser registrada  na atualizacao posterior
    @NotBlank(groups = UserView.ImagePut.class) //define qual grupo deve ser validado
    private String imageUrl;
}
