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

//    @Bean
//    public CustomAuthenticationEntryPoint customAuthenticationEntryPoint(){
//        return new CustomAuthenticationEntryPoint();
//    }
//
//    @Bean
//    public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler() {
//        return new CustomAuthenticationSuccessHandler();
//    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    @Autowired
    public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource){
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
        userDetailsManager.setUsersByUsernameQuery("SELECT username, password, enabled FROM user WHERE username = ?");
        userDetailsManager.setAuthoritiesByUsernameQuery("SELECT authority.name, users.username FROM authority JOIN users ON users.authority_id = authority.id WHERE users.username = ?");
        return userDetailsManager;
    }

//    @Bean
//    public SpringTemplateEngine templateEngine() {
//        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//        templateEngine.setTemplateResolver(templateResolver());
//        return templateEngine;
//    }
//
//    @Bean
//    public ITemplateResolver templateResolver() {
//        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
//        templateResolver.setPrefix("classpath:/Templates/");
//        templateResolver.setSuffix(".html");
//        templateResolver.setTemplateMode(TemplateMode.HTML);
//        templateResolver.setCacheable(false);
//        return templateResolver;
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeHttpRequests(
                configurer->configurer
                        // user
                        // register
                        .requestMatchers(HttpMethod.GET,"/m-user/showFormAddUser").permitAll()
                        .requestMatchers(HttpMethod.POST,"/m-user/add-process").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/m-user/auth/**").hasAnyRole("ADMIN", "MANAGER", "USER", "PARENT", "TEACHER")
                        .requestMatchers(HttpMethod.DELETE, "/m-user/auth/**").hasRole("ADMIN")
                        // class
                        .requestMatchers(HttpMethod.GET, "/m-class/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/m-class/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/m-class/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/m-class/**").hasRole("ADMIN")
                        // teacher
                        .requestMatchers(HttpMethod.GET, "/m-teacher/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/m-teacher/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/m-teacher/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/m-teacher/**").hasRole("ADMIN")
                        // note book
                        .requestMatchers(HttpMethod.GET, "/m-note/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/m-note/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/m-note/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/m-note/**").hasRole("ADMIN")
                        // note book detail
                        .requestMatchers(HttpMethod.GET, "/m-note-detail/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/m-note-detail/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/m-note-detail/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/m-note-detail/**").hasRole("ADMIN")
                        // school
                        .requestMatchers(HttpMethod.GET, "/m-school/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/m-school/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/m-school/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/m-school/**").hasRole("ADMIN")
                        // student
                        .requestMatchers(HttpMethod.GET, "/m-student/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/m-student/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/m-student/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/m-student/**").hasRole("ADMIN")
                        // parent
                        .requestMatchers(HttpMethod.GET, "/m-parent/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/m-parent/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/m-parent/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/m-parent/**").hasRole("ADMIN")
                        // scorecard
                        .requestMatchers(HttpMethod.GET, "/m-score-card/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/m-score-card/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/m-score-card/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/m-score-card/**").hasRole("ADMIN")
                        // study-record
                        .requestMatchers(HttpMethod.GET, "/m-study-record/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/m-study-record/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/m-study-record/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/m-study-record/**").hasRole("ADMIN")
                        // subject
                        .requestMatchers(HttpMethod.GET, "/m-subject/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/m-subject/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/m-subject/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/m-subject/**").hasRole("ADMIN")
                        // transcript
                        .requestMatchers(HttpMethod.GET, "/m-transcript/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/m-transcript/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/m-transcript/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/m-transcript/**").hasRole("ADMIN")

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
