package com.ead.authuser.configs;

import net.kaczmarzyk.spring.data.jpa.web.SpecificationArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
public class ResolverConfig extends WebMvcConfigurationSupport {

    /*
    Passa os parametros recebidos para tipos basicos do java
    como enums, tipos de data
     */


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        //parte da specification
        argumentResolvers.add(new SpecificationArgumentResolver());

        //agora a parte das paginacoes
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
        argumentResolvers.add(resolver);

        super.addArgumentResolvers(argumentResolvers);
    }

}
