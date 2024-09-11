package com.aaytugozkaya.carrental.repository;

import com.aaytugozkaya.carrental.entity.Support;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SupportRepository extends JpaRepository<Support, UUID> {
}
