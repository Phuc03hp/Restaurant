package phuc.devops.tech.restaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RestaurantApplication {
	public static void main(String[] args) {
		SpringApplication.run(RestaurantApplication.class, args);
	}

}
