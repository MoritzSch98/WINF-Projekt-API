package com.example.gewerbeanmeldung.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
/** 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll().anyRequest()
				.authenticated().and()
				// .formLogin().and()
				.httpBasic();
	}
**/

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/**").authorizeRequests().antMatchers("/frage").permitAll().antMatchers("/frage/{id}").permitAll()
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
		.anyRequest()
				.authenticated().and()
				// .formLogin().and()
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






	
	