package br.com.hfs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import graphql.Scalars;
import graphql.schema.GraphQLScalarType;

@SpringBootApplication
public class HefestoSpringGraphqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(HefestoSpringGraphqlApplication.class, args);
	}

	@SuppressWarnings("deprecation")
	@Bean
	public GraphQLScalarType longType() {
	    return Scalars.GraphQLLong;
	}
}
