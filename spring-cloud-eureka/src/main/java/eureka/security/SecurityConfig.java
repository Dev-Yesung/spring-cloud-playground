package eureka.security;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import lombok.Setter;

@Setter
@ConfigurationProperties("security.accounts")
@EnableWebSecurity
@Configuration
public class SecurityConfig {

	private Map<String, String> admins;

	@Bean
	public PasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests((request) -> request.anyRequest().authenticated())
			.httpBasic(Customizer.withDefaults())
			.build();
	}

	@Bean
	public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
		List<UserDetails> userDetails = admins.entrySet().stream()
			.map(adminInfo -> {
				String account = adminInfo.getKey();
				String password = passwordEncoder.encode(adminInfo.getValue());

				return User.builder()
					.username(account)
					.password(password)
					.roles("ADMIN")
					.build();
			})
			.toList();

		return new InMemoryUserDetailsManager(userDetails);
	}

}
