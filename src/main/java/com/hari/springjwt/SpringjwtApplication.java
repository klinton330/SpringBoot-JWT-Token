package com.hari.springjwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@ComponentScans({ @ComponentScan("com.hari.")
})
public class SpringjwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringjwtApplication.class, args);
	}

}
