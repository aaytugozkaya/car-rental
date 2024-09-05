package com.aaytugozkaya.carrental.repository;

import com.aaytugozkaya.carrental.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    @Query("SELECT t FROM Token t inner join User u on t.user.id = u.id where u.id =:userId and (t.isExpired = false or t.isRevoked = false)")
    List<Token> findAllValidTokensByUser(UUID userId);

    Optional<Token> findByToken(String token);
}
