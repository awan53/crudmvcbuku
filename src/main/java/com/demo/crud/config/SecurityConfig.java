package com.demo.crud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // Izinkan akses tanpa otentikasi ke halaman registrasi dan login
                        .requestMatchers("/register", "/login", "/css/**", "/js/**", "/images/**").permitAll()
                        // Semua request lain membutuhkan otentikasi
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login") // Menentukan URL halaman login kustom Anda
                        .defaultSuccessUrl("/", true) // Redirect ke homepage setelah login berhasil
                        .permitAll() // Izinkan semua untuk mengakses halaman login
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // Menentukan URL logout
                        .logoutSuccessUrl("/login?logout") // Redirect ke halaman login dengan pesan logout
                        .invalidateHttpSession(true) // Invalidasi sesi HTTP
                        .deleteCookies("JSESSIONID") // Hapus cookie sesi
                        .permitAll()
                );
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Digunakan untuk mengenkripsi password
        return new BCryptPasswordEncoder();
    }
}
