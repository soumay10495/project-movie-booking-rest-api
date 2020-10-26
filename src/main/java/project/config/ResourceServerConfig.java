package project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.DELETE, "/**").hasAuthority("ADMINISTRATOR_DELETE")
                .antMatchers(HttpMethod.POST, "/**").hasAuthority("ADMINISTRATOR_CREATE")
                .antMatchers(HttpMethod.PUT, "/**").hasAuthority("ADMINISTRATOR_UPDATE")
                .antMatchers(HttpMethod.GET, "/**").hasAnyAuthority("CUSTOMER_READ", "EMPLOYEE_READ")
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
