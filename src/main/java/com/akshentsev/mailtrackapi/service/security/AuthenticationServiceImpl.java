package com.akshentsev.mailtrackapi.service.security;

import com.akshentsev.mailtrackapi.dto.AuthenticationRequest;
import com.akshentsev.mailtrackapi.dto.AuthenticationResponse;
import com.akshentsev.mailtrackapi.dto.RegisterRequest;
import com.akshentsev.mailtrackapi.entity.TokenEntity;
import com.akshentsev.mailtrackapi.entity.UserEntity;
import com.akshentsev.mailtrackapi.repository.TokenRepository;
import com.akshentsev.mailtrackapi.repository.UserRepository;
import com.akshentsev.mailtrackapi.service.security.AuthenticationService;
import com.akshentsev.mailtrackapi.service.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    @Override
    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        var user = UserEntity.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserEntity.UserRole.CLIENT)
                .build();
        UserEntity savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    @Transactional
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getPhone(),
                            request.getPassword()
                    )
            );
            var user = repository.findByPhone(request.getPhone())
                    .orElseThrow();
            var jwtToken = jwtService.generateToken(user);
            revokeAllUserTokens(user);
            saveUserToken(user, jwtToken);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
    }

    private void saveUserToken(UserEntity user, String jwtToken) {
        var token = TokenEntity.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenEntity.TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(UserEntity user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
