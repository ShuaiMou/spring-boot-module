package com.unimelb.saul.studySpringBootSwagger.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Auther: Saul
 * @Date: 28/2/20 8:56 下午
 * @Description:
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket swaggerDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .apis(RequestHandlerSelectors.basePackage("com.unimelb.saul.studySpringBootSwagger.controller"))
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder().description("这是一个API框架")
                .contact(new Contact("Saul", "http://localhost:9090/index.html", "110@gmail.com"))
                .version("1.0.1")
                .termsOfServiceUrl("http://www.baidu.com")
                .build();
    }
}
