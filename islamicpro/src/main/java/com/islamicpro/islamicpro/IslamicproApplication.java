package com.islamicpro.islamicpro;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;

@SpringBootApplication
public class IslamicproApplication {

	public static void main(String[] args) {
		SpringApplication.run(IslamicproApplication.class, args);
		doInitialize();
	}

	public static void doInitialize(){
		try {
			FileInputStream serviceAccount =new FileInputStream
					("C:\\Users\\pqgt1683\\Desktop\\islamicpro\\src\\main\\resources\\islamicpro-firebase-adminsdk-dr3uc-b9ea522370.json");


		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setDatabaseUrl("https://islamicpro.firebaseio.com")
				.build();

		FirebaseApp.initializeApp(options);


		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {

		return builder.setConnectTimeout(Duration.ofMillis(300000))
				.setReadTimeout(Duration.ofMillis(300000)).build();
	}

}
