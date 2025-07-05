package com.pulse.footballpulse.mapper;

import com.pulse.footballpulse.domain.UserCreateDto;
import com.pulse.footballpulse.entity.UserEntity;
import com.pulse.footballpulse.entity.enums.UserRoles;
import com.pulse.footballpulse.exception.NotAcceptableException;
import com.pulse.footballpulse.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor

public class UserMapper {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
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


    public UserCreateDto getProfile(String mail){
        UserEntity user = userRepository.findByMail(mail).orElseThrow(() -> new NotAcceptableException("User not found"));
        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setFirstName(user.getFirstName());
        userCreateDto.setLastName(user.getLastName());
        userCreateDto.setBirthday(user.getBirthday());
        userCreateDto.setGender(user.getGender());
        userCreateDto.setImageUrl(user.getImageUrl());
        userCreateDto.setDescription(user.getDescription());
        userCreateDto.setUsername(user.getUsername());
        userCreateDto.setMail(user.getMail());
        return userCreateDto;
    }
}
