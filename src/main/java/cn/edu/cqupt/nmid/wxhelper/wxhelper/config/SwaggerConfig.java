package cn.edu.cqupt.nmid.wxhelper.wxhelper.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    //配置swagger
    @Bean
    public Docket docket(Environment environment){
        Profiles profiles = Profiles.of("dev");
        //2.通过environment.acceptsProfiles判断是否处于自己设定的环境
        boolean b = environment.acceptsProfiles(profiles);
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("main")
                .enable(b);
    }

    //配置swagger-ui页面上的信息
    private ApiInfo apiInfo(){
        Contact contact = new Contact("itrover", "www.itcover.cn", "1172610139@qq.com");
        return new ApiInfo("微信展览助手的api文档", "用于测试","1.0",null,contact,"Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0",new ArrayList());
    }
}
