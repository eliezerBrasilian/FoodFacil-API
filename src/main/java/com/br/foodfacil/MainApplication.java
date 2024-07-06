package com.br.foodfacil;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "FoodFacil API", version = "1", description = "API desenvolvida para o sistema de delivery FoodFacil"))

public class MainApplication {
	@Bean
	FirebaseMessaging firebaseMessaging() throws Exception{
		var googleCredentials = GoogleCredentials.fromStream(
				new ClassPathResource("foodfacil-d0c86-firebase-adminsdk-au9iz-0dfd1fa8c7.json")
						.getInputStream());

		var firebaseOptions = FirebaseOptions.builder().setCredentials(googleCredentials).build();

		var app = FirebaseApp.initializeApp(firebaseOptions,"food-facil-app");
		return FirebaseMessaging.getInstance(app);
	}

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
    }

}
