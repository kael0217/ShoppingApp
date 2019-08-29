package com.levent.pcd.config;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.stereotype.Component;

import com.levent.pcd.model.UserInfo;
import com.levent.pcd.model.UserAuth;
import com.levent.pcd.model.UserEntry;
import com.levent.pcd.repository.UserAuthRepository;
import com.levent.pcd.repository.UserInfoRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired UserDetailServiceImpl service;
	@Autowired CustomAuthenticationSuccessHandler successHandler;
	@Autowired CustomAuthenticationFailureHandler failureHandler;

//	@Bean
	public BCryptPasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder(11);
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(service).passwordEncoder(NoOpPasswordEncoder.getInstance());
	}
	public void configure(HttpSecurity http) throws Exception {
		http.formLogin().successHandler(successHandler).failureHandler(failureHandler).
		and()
		.logout().invalidateHttpSession(true).logoutUrl("/logout").logoutSuccessUrl("/login");
//		http.csrf().disable();
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
		   firewall.setAllowSemicolon(true);
		return firewall;
	}

}

@Component
class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	UserAuthRepository rep;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserAuth u=rep.findByUsername(username);
		if (u==null ||u.getPassword() == null || u.getUserRoles().isEmpty()) {
			throw new UsernameNotFoundException("Invalid username");
		} else {
			System.out.println(u);
			UserBuilder builder = org.springframework.security.core.userdetails.User.builder();
			UserDetails userDetails = builder.username(username).password(u.getPassword()).authorities(u.getUserRoles().stream()
					.map(x -> new SimpleGrantedAuthority(x.name())).collect(Collectors.toList())).build();
			return userDetails;
		}
	}
}

@Component
class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	 
    public static final String RETURN_TYPE = "html"; 
	@Autowired	UserEntry userEntry;
	@Autowired  UserInfoRepository rep;
	
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("Success");
        userEntry.setUser(rep.findByUsername(authentication.getName()));
        userEntry.isLogin = true;
        System.out.println(userEntry.getUser());
        System.out.println(userEntry.getUser().getNickname());
        System.out.println(userEntry);
        request.getSession(false).setAttribute("userEntry", userEntry);
        
        super.setDefaultTargetUrl("/");
        super.onAuthenticationSuccess(request, response, authentication);
  
    }
}

@Component
class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	 
    public static final String RETURN_TYPE = "html"; 
	@Autowired	UserEntry userEntry;
	@Autowired  UserInfoRepository rep;
	
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        System.out.println("Fail");
        System.out.println(userEntry);
        super.setDefaultFailureUrl("/login-forward");
        super.onAuthenticationFailure(request, response, exception);
  
    }
}

