package com.wcw.usercenter.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @author wcw
 * 自定义Swagger接口文档的配置
 */
@EnableSwagger2WebMvc
//@ComponentScan(basePackages = {"com.wcw.usercenter"})
@Configuration
public class SwaggerConfig {//extends WebMvcConfigurationSupport{
    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wcw.usercenter"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * api 信息
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Fancydog用户中心")//标题
                .description("Fancydog用户中心接口文档")//描述
                .termsOfServiceUrl("https://user.wcw231407.cn")//（不可见）条款地址，公司内部使用的话不需要配
                .contact(new Contact("wcw", "https://user.wcw231407.cn", "2314075528@qq.com"))//作者信息
                .version("6.6.6")//版本号
                .build();
    }
}
