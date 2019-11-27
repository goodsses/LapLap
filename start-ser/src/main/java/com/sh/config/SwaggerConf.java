package com.sh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author lxy 953250192@qq.com
 * @version 2019/4/10 17:03
 */
@Configuration
@EnableSwagger2
public class SwaggerConf {

    @Bean
    public Docket beijingRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.sh.controller.beijing"))
            .paths(PathSelectors.any())
            .build()
            .groupName("beijing");
    }

    @Bean
    public Docket shanghaiRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.sh.controller.shanghai"))
            .paths(PathSelectors.any())
            .build()
            .groupName("shanghai");
    }

    @Bean
    public Docket debRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.sh.controller.deb"))
            .paths(PathSelectors.any())
            .build()
            .groupName("deb（内部调试）");
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("LapLap", null, null);
        return new ApiInfoBuilder()
            .title("LapLap APIs")
            .contact(contact)
            .version("0.0.1")
            .build();
    }

}
