package vutran.my_first_project_spring_boot.management_student.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;
import vutran.my_first_project_spring_boot.management_student.Service.UserService;

import javax.sql.DataSource;

@Configuration
public class SecurityConfiguration  {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return  new BCryptPasswordEncoder();
    }

    // encode password form login
    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder()); // encode password
        return daoAuthenticationProvider;
    }

    @Bean
    @Autowired
    public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource){
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
        userDetailsManager.setUsersByUsernameQuery("SELECT username, password, enabled FROM user WHERE username = ?"); // jdbc, native query
        userDetailsManager.setAuthoritiesByUsernameQuery("SELECT authority.name, users.username FROM authority JOIN users ON users.authority_id = authority.id WHERE users.username = ?");
        return userDetailsManager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeHttpRequests(
                configurer->configurer
                        // user
                        // register
                        .requestMatchers(HttpMethod.GET,"/m-user/showFormAddUser").permitAll()
                        .requestMatchers(HttpMethod.POST,"/m-user/add-process").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/m-user/auth/**").hasAnyRole("ADMIN", "MANAGER", "USER", "PARENT","STUDENT", "TEACHER")
                        .requestMatchers(HttpMethod.DELETE, "/m-user/auth/**").hasAnyRole("ADMIN", "MANAGER")
                        // class
                        .requestMatchers(HttpMethod.GET, "/m-class/**").hasAnyRole("ADMIN", "MANAGER", "USER", "STUDENT", "PARENT", "TEACHER")
                        .requestMatchers(HttpMethod.POST, "/m-class/**").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/m-class/**").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/m-class/**").hasAnyRole("ADMIN")
                        // teacher
                        .requestMatchers(HttpMethod.GET, "/m-teacher/**").hasAnyRole("ADMIN", "MANAGER", "STUDENT", "USER", "PARENT", "TEACHER")
                        .requestMatchers(HttpMethod.POST, "/m-teacher/**").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/m-teacher/**").hasAnyRole("ADMIN", "MANAGER", "TEACHER")
                        .requestMatchers(HttpMethod.DELETE, "/m-teacher/**").hasRole("ADMIN")
                        // note book
                        .requestMatchers(HttpMethod.GET, "/m-note/**").hasAnyRole("ADMIN", "MANAGER", "TEACHER")
                        .requestMatchers(HttpMethod.POST, "/m-note/**").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/m-note/**").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/m-note/**").hasRole("ADMIN")
                        // note book detail
                        .requestMatchers(HttpMethod.GET, "/m-note-detail/**").hasAnyRole("ADMIN", "MANAGER", "TEACHER")
                        .requestMatchers(HttpMethod.POST, "/m-note-detail/**").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/m-note-detail/**").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/m-note-detail/**").hasRole("ADMIN")
                        // school
                        .requestMatchers(HttpMethod.GET, "/m-school/**").hasAnyRole("ADMIN", "MANAGER", "USER", "PARENT", "TEACHER")
                        .requestMatchers(HttpMethod.POST, "/m-school/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/m-school/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/m-school/**").hasRole("ADMIN")
                        // student
                        .requestMatchers(HttpMethod.GET, "/m-student/**").hasAnyRole("ADMIN", "MANAGER", "PARENT","USER", "TEACHER", "STUDENT")
                        .requestMatchers(HttpMethod.POST, "/m-student/**").hasAnyRole("ADMIN", "MANAGER", "STUDENT")
                        .requestMatchers(HttpMethod.PUT, "/m-student/**").hasAnyRole("ADMIN", "MANAGER", "STUDENT")
                        .requestMatchers(HttpMethod.DELETE, "/m-student/**").hasRole("ADMIN")
                        // parent
                        .requestMatchers(HttpMethod.GET, "/m-parent/**").hasAnyRole("ADMIN", "MANAGER", "PARENT","USER", "TEACHER", "STUDENT")
                        .requestMatchers(HttpMethod.POST, "/m-parent/**").hasAnyRole("ADMIN", "MANAGER", "PARENT")
                        .requestMatchers(HttpMethod.PUT, "/m-parent/**").hasAnyRole("ADMIN", "MANAGER", "PARENT")
                        .requestMatchers(HttpMethod.DELETE, "/m-parent/**").hasRole("ADMIN")
                        // scorecard
                        .requestMatchers(HttpMethod.GET, "/m-score-card/**").hasAnyRole("ADMIN", "MANAGER", "PARENT", "TEACHER", "STUDENT")
                        .requestMatchers(HttpMethod.POST, "/m-score-card/**").hasAnyRole("ADMIN", "MANAGER", "TEACHER")
                        .requestMatchers(HttpMethod.PUT, "/m-score-card/**").hasAnyRole("ADMIN", "MANAGER", "TEACHER")
                        .requestMatchers(HttpMethod.DELETE, "/m-score-card/**").hasAnyRole("ADMIN", "MANAGER", "TEACHER")
                        // study-record
                        .requestMatchers(HttpMethod.GET, "/m-study-record/**").hasAnyRole("ADMIN", "MANAGER", "PARENT", "TEACHER", "STUDENT")
                        .requestMatchers(HttpMethod.POST, "/m-study-record/**").hasAnyRole("ADMIN", "MANAGER", "TEACHER")
                        .requestMatchers(HttpMethod.PUT, "/m-study-record/**").hasAnyRole("ADMIN", "MANAGER", "TEACHER")
                        .requestMatchers(HttpMethod.DELETE, "/m-study-record/**").hasAnyRole("ADMIN", "MANAGER")
                        // subject
                        .requestMatchers(HttpMethod.GET, "/m-subject/**").hasAnyRole("ADMIN", "MANAGER", "PARENT", "TEACHER", "STUDENT")
                        .requestMatchers(HttpMethod.POST, "/m-subject/**").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/m-subject/**").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/m-subject/**").hasRole("ADMIN")
                        // transcript
                        .requestMatchers(HttpMethod.GET, "/m-transcript/**").hasAnyRole("ADMIN", "MANAGER", "PARENT", "TEACHER", "STUDENT")
                        .requestMatchers(HttpMethod.POST, "/m-transcript/**").hasAnyRole("ADMIN", "MANAGER", "TEACHER")
                        .requestMatchers(HttpMethod.PUT, "/m-transcript/**").hasAnyRole("ADMIN", "MANAGER", "TEACHER")
                        .requestMatchers(HttpMethod.DELETE, "/m-transcript/**").hasRole("ADMIN")

                        // change pass
                        .requestMatchers(HttpMethod.GET, "/changePassword/**").hasAnyRole("ADMIN", "MANAGER", "USER", "PARENT", "TEACHER", "STUDENT")
                        .requestMatchers(HttpMethod.POST, "/processChangePass/**").hasAnyRole("ADMIN", "MANAGER", "USER", "PARENT", "TEACHER", "STUDENT")

                        .requestMatchers(HttpMethod.GET,"/event/**").permitAll()
                        .requestMatchers("/static/**", "/JavaScript/**", "/CSS/**", "/event/showLoginPage").permitAll() // access to static not authenticate
                        .anyRequest().authenticated()
                // .authenticated()
        ).formLogin(
                form -> form.loginPage("/showLoginPage")
                        .loginProcessingUrl("/authenticateTheUser")
                        .permitAll()
        ).logout(
                logout -> logout.permitAll()
        ).exceptionHandling(
                configurer -> configurer.accessDeniedPage("/showPage403")
//                        .authenticationEntryPoint(customAuthenticationEntryPoint())
        );
        httpSecurity.httpBasic(Customizer.withDefaults());
        httpSecurity.csrf(csrf -> csrf.disable());
        // csrf chống giả mạo những phương thức, trong api này không quan trọng, không quan tâm tới trạng thái, thích gửi gì thì gửi, còn mk sẽ xử lý
        return httpSecurity.build();
    }
}
