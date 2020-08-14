package com.live.online.user.web.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.RequestHandler;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.ApiSelector;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * 类 名: SwaggerConfiguration
 * 描 述: User Web Swagger 配置类
 * 作 者: 张屹峰
 * 创 建：2020年08月14日
 * <p>
 * 历 史: (版本) 作者 时间 注释
 */
@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguration {

    // TODO 初步集成Swagger

    /** 项目名称 */
    private static final String SERVICE_NAME = "用户服务Api接口文档";

    @Bean(value = "userWebApi")
    @Order(value = 1)
    public Docket groupRestApi() {
        // 默认响应码配置
        List<ResponseMessage> defaultResponseMessages = getDefaultResponseMessages();

        // 全局统一请求参数
        List<Parameter> parameterList = new ArrayList<>();
        ParameterBuilder headerParamsBuilder = new ParameterBuilder();
        parameterList.add(headerParamsBuilder.name("Token").description("访问令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(groupApiInfo())
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET,defaultResponseMessages)
                .globalResponseMessage(RequestMethod.POST,defaultResponseMessages)
                .globalResponseMessage(RequestMethod.PUT,defaultResponseMessages)
                .globalResponseMessage(RequestMethod.DELETE,defaultResponseMessages)
                .globalOperationParameters(parameterList)
                .select()
                .apis(defaultApiSelector())
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 描 述： Swagger Api 信息
     * 作 者： 张屹峰
     * 历 史： (版本) 作者 时间 注释
     * @return 响应消息集合
     */
    private ApiInfo groupApiInfo(){
        Contact contact = new Contact("Live Online","http://www.live-online.com","");
        return new ApiInfoBuilder()
                .title(SERVICE_NAME)
                .description("<div style='font-size:14px;color:red;'>" + SERVICE_NAME + "</div>")
                .contact(contact)
                .termsOfServiceUrl("http://www.live-online.com")
                .version("2.0")
                .build();
    }

    /**
     * 描 述： 默认api文档范围
     * 作 者： 张屹峰
     * 历 史： (版本) 作者 时间 注释
     * @return 响应消息集合
     */
    private Predicate<RequestHandler> defaultApiSelector(){
        // 识别所有以 @Api 标注的Controller类，并且排除所有以 @ApiIgnore 标注的Controller类和方法
        return new ApiSelector(
                Predicates.and(
                Predicates.and(RequestHandlerSelectors.withClassAnnotation(Api.class), RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)),
                Predicates.not(RequestHandlerSelectors.withClassAnnotation(ApiIgnore.class)),
                Predicates.not(RequestHandlerSelectors.withMethodAnnotation(ApiIgnore.class))), PathSelectors.any()).getRequestHandlerSelector();
    }

    /**
     * 描 述： 获取默认响应消息
     * 作 者： 张屹峰
     * 历 史： (版本) 作者 时间 注释
     * @return 响应消息集合
     */
    private List<ResponseMessage> getDefaultResponseMessages() {
        List<ResponseMessage> responseMessageList = new ArrayList<>();
        responseMessageList.add(new ResponseMessageBuilder().code(401).message("没有权限访问").build());
        responseMessageList.add(new ResponseMessageBuilder().code(403).message("禁止访问").build());
        responseMessageList.add(new ResponseMessageBuilder().code(404).message("找不到资源").build());
        responseMessageList.add(new ResponseMessageBuilder().code(500).message("接口异常").build());

        return responseMessageList;
    }


}
