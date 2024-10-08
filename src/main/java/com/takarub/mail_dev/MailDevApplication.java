package com.takarub.mail_dev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync(proxyTargetClass = true)
public class MailDevApplication {

	public static void main(String[] args) {
		SpringApplication.run(MailDevApplication.class, args);
	}

}
