package com.pulse.footballpulse.service.impl;

import com.pulse.footballpulse.domain.LoginDto;
import com.pulse.footballpulse.domain.UserCreateDto;
import com.pulse.footballpulse.domain.response.ApiResponse;
import com.pulse.footballpulse.domain.response.JwtResponse;
import com.pulse.footballpulse.entity.UserEntity;
import com.pulse.footballpulse.exception.DataNotFoundException;
import com.pulse.footballpulse.jwt.JwtTokenService;
import com.pulse.footballpulse.mapper.UserMapper;
import com.pulse.footballpulse.repository.UserRepository;
import com.pulse.footballpulse.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtTokenService jwtTokenService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByMail(username);
    }

    @Override
    public ResponseEntity<ApiResponse<?>> signUp(UserCreateDto userDto) {
        userRepository.save(userMapper.toEntity(userDto,1234));
        return ResponseEntity.ok(ApiResponse.builder().message("Created").status(200).data(null).build());
    }

    @Override
    public ResponseEntity<ApiResponse<?>> login(LoginDto loginDto) {
       UserEntity user=userRepository.findByMail(loginDto.getMail());
       if (loginDto.getPassword().equals(user.getPassword())) {
           return ResponseEntity.ok(ApiResponse.builder()
                   .data(JwtResponse.builder().token(jwtTokenService.generateAccessToken(user)).build())
                   .message("Login in system")
                   .status(200)
                   .build());


       }
       throw new DataNotFoundException("User not found!");
    }
}
