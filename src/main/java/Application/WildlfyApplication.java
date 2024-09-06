package Application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"App","Application","Database"})
public class WildlfyApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(WildlfyApplication.class, args);
	}
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WildlfyApplication.class);
    }

}
