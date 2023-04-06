package com.forpawchain.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * http://localhost:8080/api/swagger-ui/
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	private String version = "V1";
	private String title = "A207 API " + version;

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
			.consumes(getConsumeContentTypes())
			.produces(getProduceContentTypes())

			.apiInfo(apiInfo()).groupName(version).select()
			.apis(RequestHandlerSelectors.basePackage("com.forpawchain.controller"))
			.paths(PathSelectors.any()).build()
			.securitySchemes(Arrays.asList(apiKey()))
			.securityContexts(Arrays.asList(securityContext()))
			.useDefaultResponseMessages(false);
	}

	private Set<String> getConsumeContentTypes() {
		Set<String> consumes = new HashSet<>();
		consumes.add("application/json;charset=utf-8");
		consumes.add("application/x-www-form-urlencoded");
		System.out.println(consumes);
		return consumes;
	}

	private ApiKey apiKey() {
		return new ApiKey("Authorization", "Authorization", "header");
	}
	private SecurityContext securityContext() {
		return springfox
			.documentation
			.spi.service
			.contexts
			.SecurityContext
			.builder()
			.securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build();
	}

	List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList(new SecurityReference("Authorization", authorizationScopes));
	}


	private Set<String> getProduceContentTypes() {
		Set<String> produces = new HashSet<>();
		produces.add("application/json;charset=UTF-8");
		return produces;
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title(title)
			.description("<h3>A207 API</h3>Swagger를 이용한 포포체인 API<br>")
			.license("A207 License")
			.version("1.0").build();
	}
}