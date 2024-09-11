package com.aaytugozkaya.carrental.dto.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class SupportResponse {
    private UUID id;
    private String name;
    private String email;
    private String message;
}
