package dev.danvega.runnerz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		

		// ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
		// WelcomeMessage welcomeMessage = context.getBean("welcomeMessage");

		// At first, WelcomeMessage isn't detected, even when using the
		// Object class as a workaround, Spring will say that there
		// is no bean named "welcomeMessage available"

		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
		WelcomeMessage welcomeMessage = (WelcomeMessage) context.getBean("welcomeMessage");
		System.out.println(welcomeMessage);
	}

}
