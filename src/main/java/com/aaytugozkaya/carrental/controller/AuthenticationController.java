package com.aaytugozkaya.carrental.controller;

import com.aaytugozkaya.carrental.dto.request.AuthenticationRequest;
import com.aaytugozkaya.carrental.dto.request.RegisterRequest;
import com.aaytugozkaya.carrental.dto.response.AuthenticationResponse;
import com.aaytugozkaya.carrental.dto.response.UserResponse;
import com.aaytugozkaya.carrental.service.AuthenticationService;
import com.aaytugozkaya.carrental.utils.AppConstant;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.aaytugozkaya.carrental.utils.AppConstant.BASE_URL;

@RestController
@RequestMapping(BASE_URL + "/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;
    private static final Logger logger = LogManager.getLogger(AuthenticationController.class);

    @PostMapping("/register")
    public ResponseEntity <AuthenticationResponse> register(@RequestBody RegisterRequest request){
        AuthenticationResponse response = authService.register(request);
        logger.info("User registered with email: " +  request.getEmail() + " and name : " + request.getName());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/login")
    public ResponseEntity <AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
        logger.info("User tried to log in with email: " + request.getEmail());
        return ResponseEntity.ok(authService.login(request));
    }
    @GetMapping("/get-user/{token}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("token") String token){
        return ResponseEntity.ok(authService.getUser(token));
    }
    @GetMapping("/get-user-id/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") UUID id){
        return ResponseEntity.ok(authService.getUserById(id));
    }

    @PostMapping("/add-balance/{token}/{balance}")
    public ResponseEntity<UserResponse> addBalance(@PathVariable("token") String token, @PathVariable("balance") Integer balance){
        return ResponseEntity.ok(authService.addBalance(token, balance));
    }

}
