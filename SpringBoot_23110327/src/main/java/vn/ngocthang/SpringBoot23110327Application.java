package vn.ngocthang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"vn.ngocthang.Controller"})
@ComponentScan
public class SpringBoot23110327Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBoot23110327Application.class, args);
	}

}
