package com.studydesk.Config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

@Bean(name = "studydeskOpenApi")
    public OpenAPI studydeskOpenApi() {
    return new OpenAPI()
            .components(new Components())
            .info(new Info()
                    .title("Studydesk Application API")
                    .description(
                            "studydesk API implemented with Spring Boot RESTful service and documented using springdoc-openapi and OpenAPI 3."));
}


}

