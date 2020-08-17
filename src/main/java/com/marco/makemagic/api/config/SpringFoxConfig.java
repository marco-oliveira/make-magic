package com.marco.makemagic.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Classe responsável por configurar Swagger na aplicação.
 *
 *  @author Marco Antônio
 */
@Configuration
@EnableSwagger2
public class SpringFoxConfig implements WebMvcConfigurer {

    /**
     * Retorna configurações para a documentação do swagger.
     * @return -
     */
    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.marco.makemagic.api"))
            .build()
            .useDefaultResponseMessages(false)
            .apiInfo(apiInfo())
            .tags(new Tag("Personagens", "Gerencia o Cadastro de Personagens"));
    }

    /**
     * Retorna informações padrões para o swagger.
     *
     * @return -
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Make Magic API")
            .version("1")
            .description("Backend Challenge")
            .contact(new Contact("Marco Antônio",
                    "https://www.linkedin.com/in/marcoadev/", "marco-oliveira@live.com"))
            .build();
    }

    /**
     *
     * @param registry -
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
            .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
