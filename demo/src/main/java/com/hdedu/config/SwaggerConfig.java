package com.hdedu.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfig {

//    @Bean
//    public Docket createRestApi(){
//        return new Docket(DocumentationType.SWAGGER_2)
//                .genericModelSubstitutes(DeferredResult.class)
//                .useDefaultResponseMessages(false)
//                .forCodeGeneration(false)
//                .pathMapping("/")
//                .select()
//                .build()
//                .apiInfo(productApiInfo());
//    }

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(productApiInfo())
                .groupName("分组名称")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hdedu"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo productApiInfo(){
        ApiInfo apiInfo = new ApiInfo("CRM Api接口文档",
                "这是CRM Api接口文档",
                "",
                "",
                "张大牛(17325647885)",
                "",
                "");
        return apiInfo;
    }
}
