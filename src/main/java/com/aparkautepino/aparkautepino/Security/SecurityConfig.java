package com.aparkautepino.aparkautepino.Security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.aparkautepino.aparkautepino.Model.Repository.UsuarioRepository;
import com.aparkautepino.aparkautepino.Model.Entity.Usuario;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebMvc
@EnableMethodSecurity
public class SecurityConfig {

    private final UsuarioRepository usuarioRepository;

    public SecurityConfig(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Reglas de seguridad (URLs, login y logout)

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // Autorizacion de rutas
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/css/**", "/js/**", "/images/**", "/**/*.css", "/**/*.js",
                                "/**/*.png", "/**/*.jpg")
                        .permitAll()
                        .requestMatchers("/inicio").hasAnyRole("ADMIN", "SEGURIDAD", "USUARIO")
                        .requestMatchers("/").hasAnyRole("ADMIN")
                        .requestMatchers("/").hasAnyRole("ADMIN", "SEGURIDAD")
                        .anyRequest().authenticated())

                // Login form
                .formLogin(form -> form
                        .loginPage("/login") // GET login page
                        .loginProcessingUrl("/login") // POST login
                        .defaultSuccessUrl("/inicio", true) // despues del login exitoso
                        .failureUrl("/login?error=true") // login fallido
                        .permitAll())

                // Logout
                .logout(logout -> logout
                        .logoutUrl("logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll());
        return http.build();
    }

    //Usuario desde la DB
    @Bean
    public UserDetailsService userdetailsService(){
        return username -> {
            Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Usuario no encontrado"));

        return new CustomUserDetails(usuario);
        };
    }

    // 👤 Adaptador de tu entidad User → Spring Security
    public static class CustomUserDetails implements UserDetails {

        private final Usuario usuario;

        public CustomUserDetails(Usuario usuario) {
            this.usuario = usuario;
        }

        @Override
        public List<SimpleGrantedAuthority> getAuthorities() {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_" + usuario.getRol()));
        }

        public Usuario getUsuario() {
            return this.usuario;
        }

        @Override
        public String getPassword() {
            return usuario.getPassword();
        }

        @Override
        public String getUsername() {
            return usuario.getUsername();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }

    // 🔐 3. Password encoder (OBLIGATORIO)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12); 
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
