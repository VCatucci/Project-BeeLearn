package org.generation.BeeLearn.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
public class BasicSecurityConfiguration extends WebSecurityConfigurerAdapter {

	private @Autowired UserDetailsServiceImplements service;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(service);

		auth.inMemoryAuthentication().withUser("root").password(passwordEncoder().encode("root"))
				.authorities("ROLE_ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests().antMatchers("/**").permitAll()
		        .antMatchers(HttpMethod.POST, "/api/v1/user").permitAll()
				.antMatchers(HttpMethod.PUT, "/api/v1/user/credentials").permitAll()
				.antMatchers(HttpMethod.GET, "/grup").permitAll()
                .antMatchers(HttpMethod.POST, "/grup").permitAll()
                .antMatchers(HttpMethod.PUT, "/grup").permitAll()
                .antMatchers(HttpMethod.GET, "/grup/{idGrupo}").permitAll()
                .antMatchers(HttpMethod.DELETE, "/grup/{idGrupo}").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/postagem/all").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/postagem/save").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/v1/postagem/update").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/postagem/{idPostagem}").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/v1/postagem/delete/{idPostagem}").permitAll()
				.anyRequest().authenticated().and()
				.httpBasic().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.cors().and().csrf().disable();

	}
}
