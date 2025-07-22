package com.pulse.footballpulse.config;

import com.pulse.footballpulse.entity.UserEntity;
import com.pulse.footballpulse.entity.enums.Gender;
import com.pulse.footballpulse.entity.enums.UserRoles;
import com.pulse.footballpulse.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Value;
@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataLoaderConfiguration implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${dataLoader.config}")
    private String dataLoaderStatus;

    @Override
    public void run(String... args) throws Exception {
        if (dataLoaderStatus.equals("always")) {
            userRepository.save(UserEntity.builder()
                    .firstName("admin")
                    .lastName("admin")
                    .mail("admin@gmail.com")
                    .code(null).role(UserRoles.ROLE_ADMIN)
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .birthday("1.01.1999")
                    .gender(Gender.MALE)
                    .isEnabled(true)
                    .isAccountNonExpired(true)
                    .isCredentialsNonExpired(true)
                    .isAccountNonLocked(true)
                    .build());

            userRepository.save(UserEntity.builder()
                    .firstName("user")
                    .lastName("user")
                    .mail("user@gmail.com")
                    .code(null)
                    .role(UserRoles.ROLE_USER)
                    .username("user")
                    .password(passwordEncoder.encode("user"))
                    .birthday("1.01.2000")
                    .gender(Gender.MALE)
                    .isEnabled(true)
                    .isAccountNonExpired(true)
                    .isCredentialsNonExpired(true)
                    .isAccountNonLocked(true)
                    .build());

            userRepository.save(UserEntity.builder()
                    .firstName("author")
                    .lastName("user")
                    .mail("author@gmail.com")
                    .code(null)
                    .role(UserRoles.ROLE_AUTHOR)
                    .username("author")
                    .password(passwordEncoder.encode("author"))
                    .birthday("1.01.1995")
                    .gender(Gender.FEMALE)
                    .isEnabled(true)
                    .isAccountNonExpired(true)
                    .isCredentialsNonExpired(true)
                    .isAccountNonLocked(true)
                    .build());


            log.info("Data loaded successfully");
        }
    }
}
