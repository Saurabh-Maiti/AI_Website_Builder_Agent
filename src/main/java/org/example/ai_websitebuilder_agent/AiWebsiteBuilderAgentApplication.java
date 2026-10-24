package org.example.ai_websitebuilder_agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class AiWebsiteBuilderAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiWebsiteBuilderAgentApplication.class, args);
    }

}
