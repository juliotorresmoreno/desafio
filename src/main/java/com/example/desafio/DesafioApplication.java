
package com.example.desafio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.example.desafio.security.JWTAuthorizationFilter;

@SpringBootApplication
public class DesafioApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioApplication.class, args);
	}


	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable();
			http.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
			
			http.authorizeRequests().antMatchers(HttpMethod.GET, "/").authenticated();
			http.authorizeRequests().antMatchers(HttpMethod.PUT, "/").authenticated();
			http.authorizeRequests().antMatchers(HttpMethod.POST, "/").authenticated();
			http.authorizeRequests().antMatchers(HttpMethod.PATCH, "/").authenticated();
			http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/").authenticated();

			http.authorizeRequests().antMatchers(HttpMethod.GET, "/home").permitAll();
			http.authorizeRequests().antMatchers(HttpMethod.PUT, "/home").permitAll();
			http.authorizeRequests().antMatchers(HttpMethod.POST, "/home").permitAll();
			http.authorizeRequests().antMatchers(HttpMethod.PATCH, "/home").permitAll();
			http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/home").permitAll();
		}
	}
}
