package com.pulse.footballpulse.service.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    public void authenticate(Claims claims, HttpServletRequest request)throws JsonProcessingException {
        List<String> roles= (List<String>) claims.put("roles",List.of("ROLE_USER","ROLE_ADMIN","ROLE_MODERATOR","ROLE_AUTHOR","ROLE_CLUB","ROLE_GUEST"));
        String username=claims.getSubject();


        assert roles != null;
        UsernamePasswordAuthenticationToken authenticationToken=
                new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        getRoles(roles)
                );
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    public List<SimpleGrantedAuthority>getRoles(List<String>roles){
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }
}
