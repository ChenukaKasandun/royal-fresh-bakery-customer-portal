package lk.royalfreshcustomer.securityconfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        //Service authentication matching(urls)
        http.authorizeHttpRequests(auth ->{

            auth.requestMatchers("/customerlogin/**").permitAll()
                    .requestMatchers("/jQuery/**").permitAll()
                    .requestMatchers("/bootstrap-5.2.3/**").permitAll()
                    .requestMatchers("/fontawesome-free-6.6.0-web/**").permitAll()
                    .requestMatchers("/css/**").permitAll()
                    .requestMatchers("/images/**").permitAll()
                    .requestMatchers("/script/**").permitAll()
                    .requestMatchers("/uicontroller/**").permitAll()
                    .requestMatchers("/customerdashboard").hasAnyAuthority("customer")
                    .requestMatchers("/customeronlinereg").permitAll()
                    .requestMatchers("/onlinecustomer/**").permitAll()
                    .requestMatchers("/customerlogin").permitAll()
                    .requestMatchers("/customeronlineorder").hasAnyAuthority("customer")
                    .requestMatchers("/index").permitAll()
                    .requestMatchers("/products/**").permitAll()
                    .requestMatchers("/item/**").permitAll()
                    .requestMatchers("/productionsession/**").permitAll()
                    .requestMatchers("/vehicleroute/**").permitAll()
                    .anyRequest().authenticated();

        })


        // Login details

                .formLogin(login -> {
                    login

//                            Login Process (In login page the "form action" should be same as this)
                            .loginPage("/customerlogin")
                            .defaultSuccessUrl("/customerdashboard")

                            // username and password errors
                            .failureUrl("/login?error=usernamepassworderror")// Nowadays allmost systems use common

                            .usernameParameter(("username"))
                            .passwordParameter(("password"));


                })


        //logout details
        .logout(logout -> {
            logout
                .logoutUrl("/logout")
                    .clearAuthentication(true)
                    .logoutSuccessUrl("/customerlogin");
        })

        // If any errors ---> errorpage
        // accessDeniedPage --> forward to errorpage when incorrect user try to log into
        // denied page
                .exceptionHandling(exp -> {
            exp.accessDeniedPage("/errorpage");
        })

                // In order to access data using js requests,we have to disable csrf.
                .csrf(csrf -> {
                    csrf.disable();
                });

        return http.build();

    }

    @Bean // this creates a class for BCryptPasswordEncoder and then we can call its
    // instances anywhere in the project
    // BCryptPasswordEncoder --> this is used as for oneway encryption method.You
    // can encrypt only .Cannot Decrypt(although it's an oneway encryption method,it has capability to compare the plaintext
    //and the ciphertext)
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }



}
