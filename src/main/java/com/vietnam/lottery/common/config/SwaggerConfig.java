package com.vietnam.lottery.common.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger2的接口配置
 */
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfig {

    @Bean
    public Docket createBack() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 是否启用Swagger
                .enable(true)
                // 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
                .apiInfo(apiInfo())
                // 设置哪些接口暴露给Swagger展示
                .groupName("web管理后台").globalOperationParameters(getGlobalOperationParameters()).select()
                // 扫描所有有注解的api，用这种方式更灵活
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // 扫描指定包中的swagger注解
                .apis(RequestHandlerSelectors.basePackage("com.vietnam.lottery.content.back")).paths(PathSelectors.any()).build().pathMapping("/");
    }

    @Bean
    public Docket createFront() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 是否启用Swagger
                .enable(true)
                // 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
                .apiInfo(apiInfo())
                // 设置哪些接口暴露给Swagger展示
                .groupName("web抽奖").globalOperationParameters(getGlobalOperationParameters()).select()
                // 扫描所有有注解的api，用这种方式更灵活
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // 扫描指定包中的swagger注解
                .apis(RequestHandlerSelectors.basePackage("com.vietnam.lottery.content.front")).paths(PathSelectors.any()).build().pathMapping("/");
    }

    /**
     * 添加摘要信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 设置标题
                .title("越南抽奖开发文档！")
                // 描述
                .description("越南抽奖")
                // 作者信息
                .contact("Gyan")
                // 版本
                .version("1.0.0").build();
    }

    /**
     * 配置swagger全局参数
     */
    private List<Parameter> getGlobalOperationParameters() {
        List<Parameter> pars = new ArrayList<>();
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        // header query cookie
        parameterBuilder.name("Authorization").description("token").modelRef(new ModelRef("string")).parameterType("header").required(false);
        pars.add(parameterBuilder.build());
        return pars;
    }
}
