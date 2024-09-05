package com.aaytugozkaya.carrental.service;

import com.aaytugozkaya.carrental.dto.request.AuthenticationRequest;
import com.aaytugozkaya.carrental.dto.request.RegisterRequest;
import com.aaytugozkaya.carrental.dto.response.AuthenticationResponse;
import com.aaytugozkaya.carrental.entity.enums.Role;
import com.aaytugozkaya.carrental.entity.Token;
import com.aaytugozkaya.carrental.entity.enums.TokenType;
import com.aaytugozkaya.carrental.entity.User;
import com.aaytugozkaya.carrental.exception.UserAlreadyExistException;
import com.aaytugozkaya.carrental.repository.TokenRepository;
import com.aaytugozkaya.carrental.repository.UserRepository;
import com.aaytugozkaya.carrental.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .birthDate(request.getBirthDate())
                .driverLicenseDate(request.getDriverLicenseDate())
                .driverLicenseNumber(request.getDriverLicenseNumber())
                .mobilePhone(request.getMobilePhone())
                .balance(BigDecimal.valueOf(0.00))
                .build();
        if(userRepository.existsByEmail(user.getEmail())){
            throw new UserAlreadyExistException("User already exists");
        }
        var savedUser = userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
        String jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if(validUserTokens.isEmpty()) return;

        validUserTokens.forEach(t ->{
            t.setIsExpired(true);
            t.setIsRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .isExpired(false)
                .isRevoked(false)
                .build();
        tokenRepository.save(token);
    }
}
