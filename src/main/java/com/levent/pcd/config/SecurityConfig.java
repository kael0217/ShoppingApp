package com.levent.pcd.config;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.stereotype.Component;

import com.levent.pcd.model.User;
import com.levent.pcd.model.UserRole;
import com.levent.pcd.repository.UserRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailServiceImpl service;

//	@Bean
	public BCryptPasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder(11);
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		/*
		 * auth.jdbcAuthentication().
		 * usersByUsernameQuery("select username, password from user where username = ?"
		 * )
		 * .authoritiesByUsernameQuery("select authorities from user_role where username= ?"
		 * );
		 */
		auth.userDetailsService(service).passwordEncoder(NoOpPasswordEncoder.getInstance());
	}

	public void configure(HttpSecurity http) throws Exception {
		http.formLogin().and().logout().invalidateHttpSession(true).logoutUrl("/logout").logoutSuccessUrl("/login");

		/*
		 * http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS).
		 */
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// add it
		web.httpFirewall(allowUrlEncodedSlashHttpFirewall());
	}

	@Bean
	public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
		StrictHttpFirewall firewall = new StrictHttpFirewall();
		firewall.setAllowUrlEncodedSlash(true);
		return firewall;
	}

}

//admin role id 

@Component
class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository rep;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// User u=rep.findByUsername(username);
		List<UserRole> userRoles = new ArrayList();
		userRoles.add(UserRole.USER);
		// userRoles.add(UserRole.ADMIN);
		User u = User.builder().username("admin").password("admin").userRoles(userRoles).build();
		if (u.getPassword() == null || u.getUserRoles().isEmpty()) {
			throw new UsernameNotFoundException("Invalid username");
		} else {
			System.out.println(u);
			UserBuilder builder = org.springframework.security.core.userdetails.User.builder();
			return builder.username(username).password(u.getPassword()).authorities(u.getUserRoles().stream()
					.map(x -> new SimpleGrantedAuthority("ROLE_USER")).collect(Collectors.toList())).build();

			// org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder()(username,u.getPassword(),
			// u.getUserRoles().stream().map(x-> new
			// SimpleGrantedAuthority(x.toString())).collect(Collectors.toList()));
		}
	}
}
