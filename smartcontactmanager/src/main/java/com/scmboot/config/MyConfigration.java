package com.scmboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.http.HttpServletRequest;


@Configuration
@EnableWebSecurity
public class MyConfigration{
	
	@Bean
	public UserDetailsService getDetailsService() {
		return new UserDetaillsServiceImple();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider  daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(this.getDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		
		return daoAuthenticationProvider;
	}
	
	/// configure method 
	
//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity httpSecurity ) throws Exception {
//		
//		httpSecurity
//		.csrf().disable()
//		.authenticationProvider(authenticationProvider())
//		.authorizeHttpRequests()
//		.requestMatchers("/admin/**")
//		.hasRole("ADMIN")
//		.requestMatchers("/user/**")
//		.hasRole("USER")
//		.requestMatchers("/**")
//		.permitAll()
//		.and()
//		.formLogin();
//		
//
//		return httpSecurity.build();
//		
//	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

	    httpSecurity
	        .csrf(csrf -> csrf.disable())                           // CSRF ko disable karna
	        .authenticationProvider(authenticationProvider())      // Custom authentication provider ko use karna
	        .authorizeHttpRequests(authorize -> authorize
	            .requestMatchers("/admin/**").hasRole("ADMIN")   // Admin ke liye restricted access
	            .requestMatchers("/user/**").hasRole("USER")    // User ke liye restricted access
	            .requestMatchers("/**").permitAll()            // Public access sab ke liye
	        )  
	        .formLogin(form -> form   						 // formLogin ka custom configuration
	        .loginPage("/signin") 					        // Custom login page set kar sakte hain
	        .loginProcessingUrl("/dologin")					
	        .defaultSuccessUrl("/user/index")
	        .permitAll()                                  // Login page sabko accessible hoga
	        );

	    return httpSecurity.build();
	}


	
	

}
