package com.ead.authuser.specifications;

import com.ead.authuser.models.UserModel;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;


//Filtro de Specifications da aplicacao
public class SpecificationTemplate {

    //cria um filtro levando em consideracao todos os parametros passados
    @And({
                    //filtro specification para o parametro usertype
                    @Spec(path = "userType", spec = Equal.class),
                    //filtro specification para o parametro email
                    @Spec(path = "email", spec = Like.class),
                    //filtro specification para o parametro userStatus
                    @Spec(path = "userStatus", spec = Equal.class)}
    )
    //cria um filtro com um ou outro caso desses parametros
//    @Or({
//            //filtro specification para o parametro usertype
//            @Spec(path = "userType", spec = Equal.class),
//            //filtro specification para o parametro email
//            @Spec(path = "email", spec = Like.class),
//            //filtro specification para o parametro userStatus
//            @Spec(path = "userStatus", spec = Equal.class)}
//    )
    public interface UserSpec extends Specification<UserModel> {}
}
