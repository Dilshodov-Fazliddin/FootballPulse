package com.pulse.footballpulse.mapper;

import com.pulse.footballpulse.domain.UserCreateDto;
import com.pulse.footballpulse.entity.UserEntity;
import com.pulse.footballpulse.entity.enums.UserRoles;
import com.pulse.footballpulse.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapper {
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
                .password(userCreateDto.getPassword())
                .mail(userCreateDto.getMail())
                .username(userCreateDto.getUsername())
                .code(code)
                .team(null)
                .build();
    }
}
