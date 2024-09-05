package com.aaytugozkaya.carrental.entity;

import com.aaytugozkaya.carrental.entity.enums.TokenType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "token")
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String token;
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;
    private Boolean isExpired;
    private Boolean isRevoked;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
