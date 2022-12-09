package com.example.jwt.common;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		// TODO Auto-generated method stub
////		csrf라는 방어하겠다는 의미. 이쪽으로 들어오는것은 막겟다.
//		http.csrf().disable();
//		http.httpBasic().disable().authorizeHttpRequests()
//		.antMatchers("/login**", "/web-respirces/**", "/actuator/**").permitAll()
//		.antMatchers("/admit/**").hasRole("ADMIN")
//		.antMatchers("/user/**").hasRole("USER")
//		.anyRequest().authenticated();
//		
//	http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//	}
//
//}

public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
	//	csrf라는 방어하겠다는 의미. 이쪽으로 들어오는것은 막겟다.
		http.csrf().disable();
		http.httpBasic().disable().authorizeHttpRequests()
		.antMatchers("/login**", "/web-respirces/**", "/actuator/**").permitAll()
		.antMatchers("/admit/**").hasRole("ADMIN")
		.antMatchers("/user/**").hasRole("USER")
		.anyRequest().authenticated();
		
	http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	return http.build();
	}

}


