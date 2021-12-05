package itransition.finaltask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import security.WebSecurityConfig;

@SpringBootApplication
@ComponentScan(basePackages = {"controllers", "services", "security", "utils"})
@EntityScan("entities")
@EnableJpaRepositories("repositories")
@Import(WebSecurityConfig.class)
public class FinaltaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinaltaskApplication.class, args);
    }

}
