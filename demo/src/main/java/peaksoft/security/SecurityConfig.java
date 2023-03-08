package peaksoft.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @created : Lenovo Nuriza
 **/
@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Bean
    public UserDetailsService userDetails(){
        User.UserBuilder userBuilder = User.withDefaultPasswordEncoder();
        UserDetails admin = userBuilder.username("Nuriza").password("nuriza123").roles("ADMIN").build();
        UserDetails doctor = userBuilder.username("Saltanat").password("saltanat123").roles("DOCTOR").build();
        UserDetails patient = userBuilder.username("Kurstan").password("kurstan123").roles("PATIENT").build();


        return new InMemoryUserDetailsManager(admin,doctor,patient);
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
         http.authorizeHttpRequests()

                 .requestMatchers("/hospitals/{id}/mainPage").hasAnyRole("ADMIN","DOCTOR","PATIENT")
                 .requestMatchers("/hospitals/show").hasAnyRole("ADMIN","DOCTOR","PATIENT")
                 .requestMatchers("/hospitals/new").hasRole("ADMIN")
                 .requestMatchers("/hospitals/savePage").hasRole("ADMIN")
                 .requestMatchers("/hospitals/{id}/delete").hasRole("ADMIN")
                 .requestMatchers("/hospitals/{id}/edit").hasRole("ADMIN")
                 .requestMatchers("/hospitals/{id}/update").hasRole("ADMIN")

                 .requestMatchers("/departments/{hospitalId}").permitAll()
                 .requestMatchers("/departments/{hospitalId}/new").hasRole("ADMIN")
                 .requestMatchers("/departments/{hospitalId}/savePage").hasRole("ADMIN")
                 .requestMatchers("/departments/{hospitalId}/{departmentId}/delete").hasRole("ADMIN")
                 .requestMatchers("/departments/{hospitalId}/{departmentId}/edit").hasRole("ADMIN")
                 .requestMatchers("/departments/{hospitalId}/{departmentId}/update").hasRole("ADMIN")

                 .requestMatchers("/doctors/{hospitalId}").hasAnyRole("ADMIN","DOCTOR","PATIENT")
                 .requestMatchers("/doctors/{hospitalId}/new").hasRole("ADMIN")
                 .requestMatchers("/doctors/{hospitalId}/savePage").hasRole("ADMIN")
                 .requestMatchers("/doctors/{hospitalId}/{doctorId}/assign-departments").hasRole("ADMIN")
                 .requestMatchers("/doctors/{hospitalId}/{id}/save").hasRole("ADMIN")
                 .requestMatchers("/doctors/{hospitalId}/{doctorId}/delete").hasRole("ADMIN")
                 .requestMatchers("/doctors/{hospitalId}/{doctorId}/edit").hasRole("ADMIN")
                 .requestMatchers("/doctors/{hospitalId}/{doctorId}/update").hasRole("ADMIN")

                 .requestMatchers("/patients/{hospitalId}").hasAnyRole("ADMIN","DOCTOR","PATIENT")
                 .requestMatchers("/patients/{hospitalId}/new").hasAnyRole("ADMIN","DOCTOR")
                 .requestMatchers("/patients/{hospitalId}/savePage").hasAnyRole("ADMIN","DOCTOR")
                 .requestMatchers("/patients/{hospitalId}/{patientId}/delete").hasAnyRole("ADMIN","DOCTOR")
                 .requestMatchers("/patients/{hospitalId}/{patientId}/edit").hasAnyRole("ADMIN","DOCTOR")
                 .requestMatchers("/patients/{hospitalId}/{patientId}/update").hasAnyRole("ADMIN","DOCTOR")

                 .requestMatchers("/appointments/{hospitalId}").hasAnyRole("ADMIN","DOCTOR","PATIENT")
                 .requestMatchers("/appointments/{hospitalId}/new").hasAnyRole("ADMIN","DOCTOR","PATIENT")
                 .requestMatchers("/appointments/{hospitalId}/savePage").hasAnyRole("ADMIN","DOCTOR","PATIENT")
                 .requestMatchers("/appointments/{hospitalId}/{appointmentId}/edit").hasAnyRole("ADMIN","DOCTOR","PATIENT")
                 .requestMatchers("/appointments/{hospitalId}/{appointmentId}/update").hasAnyRole("ADMIN","DOCTOR","PATIENT")
                 .requestMatchers("/appointments/{hospitalId}/{appointmentId}/delete").hasAnyRole("ADMIN","DOCTOR","PATIENT")
        .and()
        .formLogin()
        .defaultSuccessUrl("/hospitals/show")
                 .permitAll();


        return http.build();
    }
}
