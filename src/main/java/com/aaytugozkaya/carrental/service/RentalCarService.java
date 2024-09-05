package com.aaytugozkaya.carrental.service;

import com.aaytugozkaya.carrental.dto.request.RentalCarRequest;
import com.aaytugozkaya.carrental.dto.response.RentalCarResponse;
import com.aaytugozkaya.carrental.entity.RentalCar;
import com.aaytugozkaya.carrental.entity.enums.Brand;
import com.aaytugozkaya.carrental.entity.enums.Model;
import com.aaytugozkaya.carrental.exception.CarNotFoundException;
import com.aaytugozkaya.carrental.mapper.RentalCarMapper;
import com.aaytugozkaya.carrental.repository.RentalCarRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class RentalCarService {
    private final RentalCarRepository rentalCarRepository;

    public List<RentalCarResponse> getAllCars(){
        List<RentalCar> rentalCars = rentalCarRepository.findAll();
        return rentalCars.stream()
                .map(RentalCarMapper::toRentalCarResponse).collect(Collectors.toList());
    }

    public RentalCarResponse getCarById(UUID id){
        RentalCar rentalCar = rentalCarRepository.findById(id).orElseThrow(() -> new CarNotFoundException("Car not found"));
        return RentalCarMapper.toRentalCarResponse(rentalCar);
    }

    public RentalCarResponse saveRentalCar(RentalCarRequest rentalCarRequest){
        return RentalCarMapper.toRentalCarResponse(rentalCarRepository.save(RentalCarMapper.toRentalCar(rentalCarRequest)));
    }

    public RentalCarResponse updateRentalCar(UUID id, RentalCarRequest rentalCarRequest){
        RentalCar rentalCar = rentalCarRepository.findById(id).orElseThrow(() -> new CarNotFoundException("Car not found"));
        rentalCar.setBrand(rentalCarRequest.getBrand()  == null ? rentalCar.getBrand() : rentalCarRequest.getBrand());
        rentalCar.setModel(rentalCarRequest.getModel() == null ? rentalCar.getModel() : rentalCarRequest.getModel());
        rentalCar.setYear(rentalCarRequest.getYear() == null ? rentalCar.getYear() : rentalCarRequest.getYear());
        rentalCar.setDailyRentingPrice(rentalCarRequest.getDailyRentingPrice() == null ? rentalCar.getDailyRentingPrice() : rentalCarRequest.getDailyRentingPrice());
        rentalCar.setDeposit(rentalCarRequest.getDeposit() == null ? rentalCar.getDeposit() : rentalCarRequest.getDeposit());
        return RentalCarMapper.toRentalCarResponse(rentalCarRepository.save(rentalCar));
    }

    public String deleteRentalCar(UUID id){
        try {
            rentalCarRepository.deleteById(id);
            return "Car deleted";
        } catch (EntityNotFoundException e){
            throw new CarNotFoundException("Car not found");
        }
    }

    public List<Model> getModelsByBrand(Brand brand) {
        return Stream.of(Model.values())
                .filter(carModel -> carModel.getBrand().name() == brand.name())
                .collect(Collectors.toList());
    }
}
