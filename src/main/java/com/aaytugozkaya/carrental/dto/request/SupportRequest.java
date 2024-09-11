package com.aaytugozkaya.carrental.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class SupportRequest {
    @Size(min = 2, max = 255, message = "Name must be between 5 and 255 characters")
    private String name;
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    @Size(min = 5, max = 255, message = "Message must be between 5 and 255 characters")
    private String message;
}
