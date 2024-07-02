package com.chatbot.springbootchatbot;

import com.chatbot.springbootchatbot.config.CorsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(CorsConfig.class)
public class SpringbootChatbotApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootChatbotApplication.class, args);
	}

}
