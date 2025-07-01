package com.pulse.footballpulse.service.impl;

import com.pulse.footballpulse.domain.LoginDto;
import com.pulse.footballpulse.domain.UserCreateDto;
import com.pulse.footballpulse.domain.response.ApiResponse;
import com.pulse.footballpulse.domain.response.JwtResponse;
import com.pulse.footballpulse.entity.UserEntity;
import com.pulse.footballpulse.exception.DataNotFoundException;
import com.pulse.footballpulse.exception.NotAcceptableException;
import com.pulse.footballpulse.jwt.JwtTokenService;
import com.pulse.footballpulse.mapper.UserMapper;
import com.pulse.footballpulse.repository.UserRepository;
import com.pulse.footballpulse.service.EmailService;
import com.pulse.footballpulse.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EmailService emailService;
    private final JwtTokenService jwtTokenService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByMail(username).orElseThrow(() -> new DataNotFoundException("User not found"));
    }

    @Override
    public ResponseEntity<ApiResponse<?>> signUp(UserCreateDto userDto) {
        if(userRepository.existsByMail(userDto.getMail()))
            throw new NotAcceptableException("User already exists");
        int code=new Random().nextInt(1000,9000);
        emailService.sendVerificationCode(userDto.getFirstName(), userDto.getMail(), code);
        userRepository.save(userMapper.toEntity(userDto,code));
        return ResponseEntity.ok(ApiResponse.builder().message("User successfully created").status(200).data(null).build());
    }


    @Override
    public ResponseEntity<ApiResponse<?>> login(LoginDto loginDto) {
       UserEntity user=userRepository.findByMail(loginDto.getMail()).orElseThrow(()->new DataNotFoundException("User not found"));
       if (passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
           if (user.getIsEnabled()) {
               return ResponseEntity.ok(ApiResponse.builder()
                       .data(JwtResponse.builder().token(jwtTokenService.generateAccessToken(user)).build())
                       .message("Login in system")
                       .status(200)
                       .build());
           }
           throw new NotAcceptableException("Your account has blocked");
       }
       throw new NotAcceptableException("Your password is incorrect or you are not signed in");
    }

    @Override
    public ResponseEntity<ApiResponse<?>> verifyAccount(String email, Integer code) {
        UserEntity user = userRepository.findByMailAndCode(email, code).orElseThrow(() -> new DataNotFoundException("User not found"));
        user.setCode(null);
        return ResponseEntity.ok(ApiResponse.builder().status(200).message("User successfully verified").data(jwtTokenService.generateAccessToken(userRepository.save(user))).build());
    }

    @Override
    @Transactional(readOnly = true)
    // Helper method to get the current user ID from a security context
    public UUID getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Get the email from authentication (UserEntity.getUsername() returns email)
        String email = authentication.getName();

        // Find a user by email and return their UUID
        UserEntity user = userRepository.findByMail(email);
        if (user == null) {
            throw new DataNotFoundException("User not found with email: " + email);
        }

        return user.getId();
    }
}
