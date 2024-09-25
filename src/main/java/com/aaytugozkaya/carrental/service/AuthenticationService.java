package com.aaytugozkaya.carrental.service;

import com.aaytugozkaya.carrental.dto.request.AuthenticationRequest;
import com.aaytugozkaya.carrental.dto.request.RegisterRequest;
import com.aaytugozkaya.carrental.dto.response.AuthenticationResponse;
import com.aaytugozkaya.carrental.dto.response.UserResponse;
import com.aaytugozkaya.carrental.entity.enums.Role;
import com.aaytugozkaya.carrental.entity.Token;
import com.aaytugozkaya.carrental.entity.enums.TokenType;
import com.aaytugozkaya.carrental.entity.User;
import com.aaytugozkaya.carrental.exception.AuthenticationFailedException;
import com.aaytugozkaya.carrental.exception.UserAlreadyExistException;
import com.aaytugozkaya.carrental.mapper.UserMapper;
import com.aaytugozkaya.carrental.repository.TokenRepository;
import com.aaytugozkaya.carrental.repository.UserRepository;
import com.aaytugozkaya.carrental.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private static final Logger logger = LogManager.getLogger(AuthenticationService.class);

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
        try {
            // Try to authenticate the user with the provided credentials
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            // If authentication is successful, retrieve the user
            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Generate a JWT token and save it
            String jwtToken = jwtService.generateToken(user);
            revokeAllUserTokens(user);
            saveUserToken(user, jwtToken);

            // Return the authentication response containing the JWT token
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();

        } catch (AuthenticationException e) {
            // Throw custom exception for invalid credentials
            logger.info("User tried to log in with email: " + request.getEmail() + " but failed");
            throw new AuthenticationFailedException("Invalid email or password");

        }
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

    public UserResponse getUser(String token){
        User user = tokenRepository.findUserIdByToken(token)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.toUserResponse(user);
    }
    public UserResponse getUserById(UUID id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.toUserResponse(user);

    }

    public UserResponse addBalance(String token, Integer balance) {
        User user = tokenRepository.findUserIdByToken(token)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setBalance(user.getBalance().add(BigDecimal.valueOf(balance)));
        userRepository.save(user);
        logger.info("User with id: " + user.getId() + " added " + balance + " to their balance");
        return UserMapper.toUserResponse(user);
    }
}
