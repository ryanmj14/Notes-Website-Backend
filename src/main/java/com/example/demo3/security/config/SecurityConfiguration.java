package com.example.demo3.security.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.demo3.jwt.authentication.JwtAuthenticationEntryPoint;
import com.example.demo3.jwt.authentication.JwtRequestFilter;
import com.example.demo3.jwt.authentication.JwtUserDetailsService;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration {
	
    @Autowired
    private final JwtRequestFilter jwtRequestFilter;
    @Autowired
    private final JwtUserDetailsService jwtUserDetailsService;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    
                    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	http
    		.formLogin().permitAll().and()
        	.cors().and()
    		.csrf().disable()
	        .authorizeRequests()
	        	.antMatchers("/api/v*/registration/**").permitAll()
	        	.antMatchers("/authenticate/**").permitAll()
	        	.antMatchers("/Login/**").permitAll()
	        	.antMatchers("/logout/**").permitAll()
	            .anyRequest()
		        .authenticated()
		        .and()

		        .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    	
		         http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
		         .cors().configurationSource(corsConfigurationSource());
		      		         
    	return http.build();
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    
    @Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
      return authConfiguration.getAuthenticationManager();
    }
    
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
