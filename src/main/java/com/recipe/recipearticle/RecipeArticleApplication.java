package com.recipe.recipearticle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RecipeArticleApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecipeArticleApplication.class, args);
	}

}
