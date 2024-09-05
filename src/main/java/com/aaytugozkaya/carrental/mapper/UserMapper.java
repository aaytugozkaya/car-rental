package com.aaytugozkaya.carrental.mapper;

import com.aaytugozkaya.carrental.dto.response.UserResponse;
import com.aaytugozkaya.carrental.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserMapper {
    public static UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .mobilePhone(user.getMobilePhone())
                .balance(user.getBalance())
                .birthDate(user.getBirthDate())
                .driverLicenseDate(user.getDriverLicenseDate())
                .role(user.getRole())
                .driverLicenseNumber(user.getDriverLicenseNumber())
                .build();
    }
}
