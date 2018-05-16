package com.cherkasov.configuration;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.cherkasov.controllers"))
//                .paths(PathSelectors.any())
                .paths(regex("/api/v1/.*"))
                .build()
                .apiInfo(apiInfo()//)
//                .useDefaultResponseMessages(false)
//                .globalResponseMessage(RequestMethod.GET,
//                        Lists.newArrayList(
//                                new ResponseMessageBuilder()
//                                        .code(500)
//                                        .message("500 message")
//                                        .responseModel(new ModelRef("Error"))
//                                        .build(),
//                                new ResponseMessageBuilder()
//                                        .code(403)
//                                        .message("Forbidden!")
//                                        .build()
//                        )
                );
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Smart Home - REST API",
                "REST API interface and documentation.",
                null,
                null,
                new Contact("Alexey Cherkasov", "https://cherkasov.com", "hawk_san@mail.ru"),
                null,
                null,
                Collections.emptyList()
        );
    }

}