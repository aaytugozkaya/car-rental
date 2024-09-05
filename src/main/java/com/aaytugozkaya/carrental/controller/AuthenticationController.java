package com.aaytugozkaya.carrental.controller;

import com.aaytugozkaya.carrental.dto.request.AuthenticationRequest;
import com.aaytugozkaya.carrental.dto.request.RegisterRequest;
import com.aaytugozkaya.carrental.dto.response.AuthenticationResponse;
import com.aaytugozkaya.carrental.service.AuthenticationService;
import com.aaytugozkaya.carrental.utils.AppConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.aaytugozkaya.carrental.utils.AppConstant.BASE_URL;

@RestController
@RequestMapping(BASE_URL + "/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity <AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));
    }
    @PostMapping("/login")
    public ResponseEntity <AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authService.login(request));
    }
}
