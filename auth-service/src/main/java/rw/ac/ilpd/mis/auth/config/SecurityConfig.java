package rw.ac.ilpd.mis.auth.config;

import com.nimbusds.jose.jwk.RSAKey;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rw.ac.ilpd.mis.auth.entity.jpa.PrivilegeEntity;
import rw.ac.ilpd.mis.auth.entity.jpa.RoleEntity;
import rw.ac.ilpd.mis.auth.entity.jpa.UnitEntity;
import rw.ac.ilpd.mis.auth.entity.jpa.UserEntity;
import rw.ac.ilpd.mis.auth.repository.jpa.PrivilegeRepository;
import rw.ac.ilpd.mis.auth.repository.jpa.RoleRepository;
import rw.ac.ilpd.mis.auth.repository.jpa.UnitRepository;
import rw.ac.ilpd.mis.auth.repository.jpa.UserRepository;
import rw.ac.ilpd.mis.auth.security.jwt.AuthEntryPointJwt;
import rw.ac.ilpd.mis.auth.security.jwt.AuthTokenFilter;
import rw.ac.ilpd.mis.auth.security.jwt.RestAccessDeniedHandler;
import rw.ac.ilpd.mis.auth.security.jwt.JwtUtils;
import rw.ac.ilpd.mis.auth.service.impl.UserDetailsServiceImpl;
import rw.ac.ilpd.mis.auth.service.impl.mapper.PrivilegeMapper;
import rw.ac.ilpd.mis.shared.config.MisConfig;
import rw.ac.ilpd.mis.shared.config.ServiceID;
import rw.ac.ilpd.mis.shared.dto.user.Privilege;
import rw.ac.ilpd.mis.shared.enums.Gender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.LocalDate;
import java.util.*;




@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

    @Value("${spring.app.jwt.public-key}")
    RSAPublicKey publicKey;

    @Value("${spring.app.jwt.private-key}")
    RSAPrivateKey privateKey;

    private static final String PRIVILEGE_PACKAGE = "rw.ac.ilpd.mis.shared.config.privilege";

    @Autowired private PrivilegeRepository privilegeRepository;

    // ==== JWT FILTER BEAN (constructor injection) ====
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter(JwtUtils jwtUtils,
                                                        UserDetailsServiceImpl userDetailsService,
                                                        AuthEntryPointJwt unauthorizedHandler) {
        // This matches your new filter’s constructor (JwtUtils, UserDetailsServiceImpl, AuthenticationEntryPoint)
        return new AuthTokenFilter(jwtUtils, userDetailsService, unauthorizedHandler);
    }

    // ==== SECURITY CHAIN ====
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http,
                                                   AuthEntryPointJwt unauthorizedHandler,
                                                   RestAccessDeniedHandler accessDeniedHandler,
                                                   AuthTokenFilter authTokenFilter) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // ✅ Public docs
                        .requestMatchers(MisConfig.SWAGGER_WHITELIST).permitAll()
                        .requestMatchers(MisConfig.PREFIXED).permitAll()

                        // your existing public endpoints
                        .requestMatchers(
                                "/api/v1/auth/public/**",
                                "/public/**"
                        ).permitAll()

                        // CORS preflight
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // everything else is secured
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(unauthorizedHandler)
                        .accessDeniedHandler(accessDeniedHandler)
                )
                .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    RSAKey misKey() {
        return new RSAKey.Builder(publicKey).privateKey(privateKey).build();
    }

    // =========================
    // ... the rest of your config: privilege scan, data init runner, etc.
    // (unchanged; omitted here for brevity)
    // =========================

    // --- your existing scan/load/privilege creation methods remain as-is ---
    private Set<Class<?>> scanForClasses(String basePackage) throws IOException, ClassNotFoundException {
        Reflections reflections = new Reflections(basePackage, new SubTypesScanner(false));
        return reflections.getSubTypesOf(Object.class);
    }

    private Map<String, String> loadPrivilegesFromClass(Class<?> clazz) {
        Map<String, String> fieldMap = new LinkedHashMap<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers()) && Modifier.isFinal(field.getModifiers())) {
                try {
                    field.setAccessible(true);
                    Object value = field.get(null);
                    fieldMap.put(field.getName(), String.valueOf(value));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Failed to read field: " + field.getName(), e);
                }
            }
        }
        return fieldMap;
    }

    public static String toTitleCase(String input) {
        if (input == null || input.isBlank()) return input;
        String[] parts = input.split("_");
        StringBuilder result = new StringBuilder();
        for (String part : parts) {
            if (part.isEmpty()) continue;
            result.append(Character.toUpperCase(part.charAt(0)))
                    .append(part.substring(1).toLowerCase())
                    .append(" ");
        }
        return result.toString().trim();
    }

    private List<Privilege> createServicePrivileges(String basePackage){
        try {
            Set<Class<?>> classes = scanForClasses(basePackage);
            List<Privilege> privileges = new ArrayList<>();
            for (Class<?> clazz : classes) {
                Map<String, String> fieldMap = loadPrivilegesFromClass(clazz);
                for (String entry : fieldMap.keySet()) {
                    if (!entry.endsWith("_PATH")) {
                        String value = fieldMap.get(entry);
                        String pckg = clazz.getPackage().getName();
                        Privilege pv = new Privilege();
                        pv.setName(value);
                        pv.setDescription(toTitleCase(value));
                        pv.setPath(fieldMap.get(entry + "_PATH"));
                        pv.setService(pckg.substring(pckg.lastIndexOf(".") + 1));
                        privileges.add(pv);
                    }
                }
            }
            LOGGER.info("Privileges added {}", privileges);
            return privileges;
        } catch (Exception e) {
            LOGGER.error("Failed to create privileges from classes.", e);
            return List.of();
        }
    }

    @Bean
    public CommandLineRunner initData(RoleRepository roleRepository,
                                      UserRepository userRepository,
                                      PrivilegeRepository privilegeRepository,
                                      PrivilegeMapper privilegeMapper,
                                      PasswordEncoder passwordEncoder,
                                      UnitRepository unitRepository) {
        return args -> {
            List<Privilege> auth_privileges = createServicePrivileges(PRIVILEGE_PACKAGE);
            for (Privilege privilege : auth_privileges) {
                if (!privilegeRepository.existsByName(privilege.getName())) {
                    privilegeRepository.save(privilegeMapper.apiToEntity(privilege));
                }
            }

            UnitEntity unit = unitRepository.findByName("SUPER UNIT")
                    .orElseGet(() -> unitRepository.save(new UnitEntity("SUPER UNIT", "SU", "Unit of Super User of the System. A technical Unit")));

            RoleEntity superadmin = roleRepository.findByName("SuperAdmin")
                    .orElseGet(() -> roleRepository.save(new RoleEntity("SuperAdmin", unit)));

            PrivilegeEntity superPriv = privilegeRepository.findByName("super_admin")
                    .orElseGet(() -> privilegeRepository.save(new PrivilegeEntity("super_admin", "Super Admin", "", ServiceID.AuthSvc.toString())));

            RoleEntity publicRole = roleRepository.findByName("Public")
                    .orElseGet(() -> roleRepository.save(new RoleEntity("Public", unit)));

            PrivilegeEntity publicPriv = privilegeRepository.findByName("public")
                    .orElseGet(() -> privilegeRepository.save(new PrivilegeEntity("public", "Public User", "", ServiceID.AuthSvc.toString())));


            Set<RoleEntity> roles = new HashSet<>();
            Set<PrivilegeEntity> privileges = new HashSet<>();
            privileges.add(superPriv);
            superadmin.setPrivileges(privileges);
            roleRepository.save(superadmin);

            if (!userRepository.existsByUsername("superadmin")) {
                UserEntity superAdmin = new UserEntity();
                superAdmin.setNames("SUPER ADMIN");
                superAdmin.setUsername("superadmin");
                superAdmin.setEmail("superadmin@mis.ilpd.ac.rw");
                superAdmin.setGender(Gender.MALE);
                superAdmin.setPassword(passwordEncoder.encode("superAdminPass"));
                superAdmin.setAccountNonLocked(true);
                superAdmin.setAccountNonExpired(true);
                superAdmin.setCredentialsNonExpired(true);
                superAdmin.setActive(true);
                superAdmin.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
                superAdmin.setAccountExpiryDate(LocalDate.now().plusYears(1));
                superAdmin.setTwoFactorEnabled(false);
                superAdmin.setSignUpMethod("email");
                roles.add(superadmin);
                superAdmin.setRoles(roles);
                userRepository.save(superAdmin);
            }
        };
    }
}