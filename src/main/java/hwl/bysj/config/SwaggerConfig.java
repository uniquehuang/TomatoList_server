package hwl.bysj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
//开启Swagger

@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket docketUser(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("番茄清单")
                .select()
                .apis(RequestHandlerSelectors.basePackage("hwl.bysj.controller"))
                .build();
    }
    private ApiInfo apiInfo(){
        //contact为作者信息
        Contact contact=new Contact("hwl","https://blog.csdn.net/weixin_51964627?spm=1010.2135.3001.5343","594064930@qq.com");
        return new ApiInfo(
                "毕业设计-番茄清单app", //对应title
                "时间任务管理类类app", //对应描述
                "1.0", //对应版本
                "https://blog.csdn.net/weixin_51964627?spm=1010.2135.3001.5343",  //组织的url
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }
}
