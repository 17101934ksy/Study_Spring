package backend.backend;

import backend.backend.userauth.config.properties.AppProperties;
import backend.backend.userauth.config.properties.CorsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(scanBasePackages = {"backend.backend.userauth.config.properties"})
@EnableConfigurationProperties({
		CorsProperties.class,
		AppProperties.class
})
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}
