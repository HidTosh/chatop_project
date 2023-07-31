package com.api.chatop;

import com.api.chatop.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class ChatopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatopApplication.class, args);
	}

}
