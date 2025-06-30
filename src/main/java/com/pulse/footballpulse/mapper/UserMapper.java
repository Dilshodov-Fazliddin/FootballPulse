package com.pulse.footballpulse.mapper;

import com.pulse.footballpulse.domain.UserCreateDto;
import com.pulse.footballpulse.entity.UserEntity;
import com.pulse.footballpulse.entity.enums.UserRoles;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class UserMapper {
    private final PasswordEncoder passwordEncoder;
    public UserEntity toEntity(UserCreateDto userCreateDto,Integer code) {
        return UserEntity.builder()
                .firstName(userCreateDto.getFirstName())
                .lastName(userCreateDto.getLastName())
                .birthday(userCreateDto.getBirthday())
                .role(UserRoles.ROLE_USER)
                .gender(userCreateDto.getGender())
                .imageUrl(userCreateDto.getImageUrl())
                .description(userCreateDto.getDescription())
                .password(passwordEncoder.encode(userCreateDto.getPassword()))
                .mail(userCreateDto.getMail())
                .username(userCreateDto.getUsername())
                .code(code)
                .team(null)
                .isAccountNonExpired(true)
                .isEnabled(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .build();
    }
}
