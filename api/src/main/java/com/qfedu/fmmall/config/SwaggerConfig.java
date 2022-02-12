package com.qfedu.fmmall.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.w3c.dom.Document;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    /**
     * 1.配置生成的文档信息
     * 2.配置生成规则
     *
     */

    /**
     *封装接口文档信息
     * @return
     */
    @Bean
    public Docket getDocket(){
        ApiInfoBuilder apiInfoBuilder=new ApiInfoBuilder();

        //指定封面信息
        apiInfoBuilder.title("蜂蜜商城后端接口说明")
                .version("v 2.0.1")
                .description("此文档详细说明了蜂蜜商城项目后端接口规范")
                .contact(new Contact("HandSomeB","www.fuck.com","handsomeb@foxmail.com"));
        ApiInfo build = apiInfoBuilder.build();

        //指定生成的文档的封面信息，包括 标题、版本、作者
        //指定文档风格
        Docket docket=new Docket(DocumentationType.SWAGGER_2)
                //指定文档封面信息
                .apiInfo(build)
                //选择哪些接口生成文档
                .select()
                //指定扫描的接口位置
                .apis(RequestHandlerSelectors.basePackage("com.qfedu.fmmall.controller"))
                //对所有请求的接口生成
                .paths(PathSelectors.any())
                .build();

        return docket;
    }

}
