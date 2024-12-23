package com.qfwebsite.rsx;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author lv.yp
 * @Date 2017-10-17
 */
@Configuration
@EnableSwagger2
public class Swagger2 {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.qfwebsite.rsx.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("在线文档--用户相关后台接口")
                .description("公共返回参数:1, SUCCESS; 2, FAILED; 3, PARAM ERROR; 4, INNER ERROR; 5, TIME OUT; 6, AUTH FAILED")
                .termsOfServiceUrl("")
                .contact("zhuangyuehui")
                .version("1.0")
                .build();
    }
}
