package com.training_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

  @Bean
  OpenAPI customOpenAPI() {
    Info apiInfo = new Info()
        .version("1.0.0")
        .title("E Learning System API");

    return new OpenAPI().info(apiInfo);
  }

}
