package com.example.gewerbeanmeldung.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//@EnableGlobalMethodSecurity(prePostEnabled = true)  

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
		configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	
	
/** 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll().anyRequest()
				.authenticated().and()
				// .formLogin().and()
				.httpBasic();
	}

		http.csrf().disable().cors().and().antMatcher("/**").authorizeRequests().antMatchers("/frage").permitAll().antMatchers("/frage/{id}").permitAll()
		.antMatchers("/type/{formType}").permitAll().antMatchers("/category/all").permitAll()
		.antMatchers("/type/{formType}/category/{category}").permitAll().antMatchers("/type/{form_id}/category/{category_id}/findstart").permitAll()
		.antMatchers("/category/{category}").permitAll().antMatchers("/frage/{id}/choices").permitAll()
		.antMatchers("/filled/forms/all").permitAll().antMatchers("/filled/forms/{form_id}").permitAll()
		.antMatchers("/filled/forms/add").permitAll().antMatchers("/filled/forms/{form_id}/update").permitAll()
		.antMatchers("/filled/forms/{form_id}/delete").permitAll().antMatchers("/filled/forms/{form_id}").permitAll()
		.antMatchers("/forms/all").permitAll().antMatchers("/downloadFile/{fileId:.+}").permitAll()
		.antMatchers("/uploadFile/{answerId}").permitAll().antMatchers("/uploadMultipleFiles/{answerId}").permitAll()
		.antMatchers("/uploadFile/{id}/{answerId}/update").permitAll().antMatchers("forms/{form_id}/question/{question_id}/answers/add").permitAll()
		.antMatchers("/forms/{form_id}/question/{question_id}/answers/{answers_id}/edit").permitAll().antMatchers("/forms/{form_id}/question/{question_id}/answers").permitAll()
		.antMatchers("/forms/{form_id}/answers/all/delete").permitAll().antMatchers("/forms/{form_id}/answers/all/add").permitAll()
		.antMatchers("/forms/{form_id}/answers/all/edit").permitAll()
		.anyRequest()
				.authenticated().and()
				//.formLogin().and()
				.httpBasic();


				http.csrf().disable().cors().and().antMatcher("/**").authorizeRequests().antMatchers("/frage").permitAll().antMatchers("/frage/{id}").permitAll()
		.antMatchers("/type/{formType}").permitAll().antMatchers("/category/all").permitAll()
		.antMatchers("/type/{formType}/category/{category}").permitAll().antMatchers("/type/{form_id}/category/{category_id}/findstart").permitAll()
		.antMatchers("/category/{category}").permitAll().antMatchers("/frage/{id}/choices").permitAll()
		.antMatchers("/filled/forms/all").permitAll().antMatchers("/filled/forms/{form_id}").permitAll()
		.antMatchers("/filled/forms/add").permitAll().antMatchers("/filled/forms/{form_id}/update").permitAll() 
		.antMatchers("/filled/forms/{form_id}/delete").permitAll().antMatchers("/filled/forms/{form_id}").permitAll()
		.antMatchers("/forms/all").permitAll().antMatchers("/downloadFile/{fileId:.+}").permitAll()
		.antMatchers("/uploadFile/{answerId}").permitAll().antMatchers("/uploadMultipleFiles/{answerId}").permitAll()
		.antMatchers("/uploadFile/{id}/{answerId}/update").permitAll().antMatchers("/forms/{form_id}/question/{question_id}/answers/add").permitAll()
		.antMatchers("/forms/{form_id}/question/{question_id}/answers/{answers_id}/edit").permitAll().antMatchers("/forms/{form_id}/question/{question_id}/answers").permitAll()
		.antMatchers("/forms/{form_id}/answers/all/delete").permitAll().antMatchers("/forms/{form_id}/answers/all/add").permitAll()
		.antMatchers("/forms/{form_id}/answers/all/edit").permitAll()
		//.antMatchers("/api/v1/basicauth").permitAll()
		.anyRequest()
				.authenticated().and()
				.httpBasic();
**/

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().configurationSource(corsConfigurationSource()).and().antMatcher("/**").authorizeRequests().antMatchers("/fragen").permitAll().antMatchers("/fragen/{id}").permitAll()
		.antMatchers("/type/{formType}").permitAll().antMatchers("/category/all").permitAll()
		.antMatchers("/type/{formType}/category/{category}").permitAll().antMatchers("/type/{form_id}/category/{category_id}/findstart").permitAll()
		.antMatchers("/category/{category}").permitAll().antMatchers("/frage/{id}/choices").permitAll()
		.antMatchers("/filled/forms/all").permitAll().antMatchers("/filled/forms/{form_id}").permitAll()
		.antMatchers("/filled/forms/add").permitAll().antMatchers("/filled/forms/{form_id}/update").permitAll() 
		.antMatchers("/filled/forms/{form_id}/delete").permitAll().antMatchers("/filled/forms/{form_id}").permitAll()
		.antMatchers("/forms/all").permitAll().antMatchers("/downloadFile/{fileId:.+}").permitAll()
		.antMatchers("/uploadFile/{answerId}").permitAll().antMatchers("/uploadMultipleFiles/{answerId}").permitAll()
		.antMatchers("/uploadFile/{id}/{answerId}/update").permitAll().antMatchers("/forms/{form_id}/question/{question_id}/answers/add").permitAll()
		.antMatchers("/forms/{form_id}/question/{question_id}/answers/{answers_id}/edit").permitAll().antMatchers("/forms/{form_id}/question/{question_id}/answers").permitAll()
		.antMatchers("/forms/{form_id}/answers/all/delete").permitAll().antMatchers("/forms/{form_id}/answers/all/add").permitAll()
		.antMatchers("/forms/{form_id}/answers/all/edit").permitAll().antMatchers("/form/{form_id}/category/{category_id}/findstart").permitAll()
		//.antMatchers("/api/v1/basicauth").permitAll()
		.anyRequest()
				.authenticated().and()
				.httpBasic();
	}
	
	
}







/**
 * 
 * .antMatchers("/type/{formType}/category/{category}").permitAll()
		.antMatchers("/type/{form_id}/category/{category_id}/findstart").permitAll()
		.antMatchers("category/{category}").permitAll()
		.antMatchers("category/all").permitAll().
 * 
 * 
 */






	
	
