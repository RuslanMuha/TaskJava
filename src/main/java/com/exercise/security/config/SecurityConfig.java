package com.exercise.security.config;

import com.exercise.quotes.utils.ApiQuotesConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(WebSecurity web) {
		web.ignoring()
				.antMatchers("/h2-console/**/**");

	}


@Override
protected void configure(HttpSecurity httpSecurity) throws Exception{



	httpSecurity.httpBasic();
	httpSecurity.csrf().disable();
	httpSecurity.authorizeRequests().antMatchers(ApiQuotesConstants.GET_ALL_QUOTES).authenticated();
	httpSecurity.authorizeRequests().antMatchers(ApiQuotesConstants.GET_QUOTE).authenticated();
	httpSecurity.authorizeRequests().antMatchers(ApiQuotesConstants.ADD_QUOTE).hasAnyRole("USER","ADMIN");
	httpSecurity.authorizeRequests().antMatchers(ApiQuotesConstants.UPDATE_QUOTE).hasAnyRole("USER","ADMIN");
	httpSecurity.authorizeRequests().antMatchers(ApiQuotesConstants.DELETE_QUOTE).hasAnyRole("USER","ADMIN");

	httpSecurity.authorizeRequests().antMatchers(ApiQuotesConstants.GET_ALL_ITEMS).authenticated();
	httpSecurity.authorizeRequests().antMatchers(ApiQuotesConstants.GET_ITEM).authenticated();
	httpSecurity.authorizeRequests().antMatchers(ApiQuotesConstants.ADD_ITEM).hasAnyRole("USER","ADMIN");
	httpSecurity.authorizeRequests().antMatchers(ApiQuotesConstants.UPDATE_ITEM).hasAnyRole("USER","ADMIN");
	httpSecurity.authorizeRequests().antMatchers(ApiQuotesConstants.DELETE_ITEM).hasAnyRole("USER","ADMIN");

}
}
