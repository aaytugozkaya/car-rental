package com.aaytugozkaya.carrental.repository;

import com.aaytugozkaya.carrental.entity.RentalCar;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface RentalCarRepository extends JpaRepository<RentalCar, UUID> {
}
