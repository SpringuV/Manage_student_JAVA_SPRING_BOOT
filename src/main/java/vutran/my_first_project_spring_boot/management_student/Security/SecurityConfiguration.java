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

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        return templateEngine;
    }

    @Bean
    public ITemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix("classpath:/Templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeHttpRequests(
                configurer->configurer
                        // user
                        .requestMatchers(HttpMethod.GET, "/api-user/**").hasAnyRole("ADMIN", "MANAGER", "STUDENT", "USER", "PARENT", "TEACHER")
                        .requestMatchers(HttpMethod.POST, "/api-user/**").hasAnyRole("ADMIN", "MANAGER", "STUDENT", "PARENT", "TEACHER")
                        .requestMatchers(HttpMethod.PUT, "/api-user/**").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/api-user/**").hasRole("ADMIN")
                        // class
                        .requestMatchers(HttpMethod.GET, "/api-classes/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api-classes/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api-classes/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api-classes/**").hasRole("ADMIN")
                        // teacher
                        .requestMatchers(HttpMethod.GET, "/m-teacher/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/m-teacher/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/m-teacher/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/m-teacher/**").hasRole("ADMIN")
                        // note book
                        .requestMatchers(HttpMethod.GET, "/api-note/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api-note/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api-note/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api-note/**").hasRole("ADMIN")
                        // school
                        .requestMatchers(HttpMethod.GET, "/api-school/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api-school/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api-school/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api-school/**").hasRole("ADMIN")
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
                        .requestMatchers(HttpMethod.GET, "/api-score-card/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api-score-card/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api-score-card/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api-score-card/**").hasRole("ADMIN")
                        // student card
                        .requestMatchers(HttpMethod.GET, "/api-student-card/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api-student-card/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api-student-card/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api-student-card/**").hasRole("ADMIN")
                        // study-record
                        .requestMatchers(HttpMethod.GET, "/api-study-record/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api-study-record/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api-study-record/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api-study-record/**").hasRole("ADMIN")
                        // subject
                        .requestMatchers(HttpMethod.GET, "/api-subject/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api-subject/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api-subject/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api-subject/**").hasRole("ADMIN")
                        // transcript
                        .requestMatchers(HttpMethod.GET, "/api-transript/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api-transript/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api-transript/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api-transript/**").hasRole("ADMIN")
                        // register
                        .requestMatchers("/m-user/showFormAddUser").permitAll()
                        .requestMatchers("/m-class/getClassBySchoolId/**").permitAll()
                        .requestMatchers("/m-student/getStudentByClassAndSchool/**").permitAll()
                        .requestMatchers("/m-subject/getSubjectBySchool/**").permitAll()
                        .requestMatchers("/m-teacher/getTeacherBySchoolAndClass/**").permitAll()
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
