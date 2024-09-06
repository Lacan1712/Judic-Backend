package Application.ConfiJPA;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "Database.Repository")
@EntityScan(basePackages="database.Entities")
public class ConfigurationJPA {
}
