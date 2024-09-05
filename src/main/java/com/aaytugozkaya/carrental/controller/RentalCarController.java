package com.aaytugozkaya.carrental.controller;

import com.aaytugozkaya.carrental.dto.request.RentalCarRequest;
import com.aaytugozkaya.carrental.dto.response.RentalCarResponse;
import com.aaytugozkaya.carrental.entity.enums.Brand;
import com.aaytugozkaya.carrental.entity.enums.Model;
import com.aaytugozkaya.carrental.service.RentalCarService;
import com.aaytugozkaya.carrental.utils.ErrorUtils;
import com.aaytugozkaya.carrental.validator.RentalCarValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.aaytugozkaya.carrental.utils.AppConstant.BASE_URL;

@RestController
@RequestMapping(BASE_URL + "/rental")
@RequiredArgsConstructor
public class RentalCarController {
    private final RentalCarService rentalCarService;
    private final RentalCarValidator rentalCarValidator;

    @GetMapping("/allCars")
    public ResponseEntity<List<RentalCarResponse>> getAllCars(){
        return ResponseEntity.ok(rentalCarService.getAllCars());
    }
    @GetMapping("/{id}")
    public ResponseEntity<RentalCarResponse> getCarById(@PathVariable UUID id){
        return ResponseEntity.ok(rentalCarService.getCarById(id));
    }
    @PostMapping
    public ResponseEntity<?> saveRentalCar(@RequestBody RentalCarRequest rentalCarRequest, BindingResult bindingResult){
        rentalCarValidator.validate(rentalCarRequest, bindingResult);
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = ErrorUtils.getErrorMessages(bindingResult);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("errors", errorMessages));
        }

        return ResponseEntity.ok(rentalCarService.saveRentalCar(rentalCarRequest));
    }
    @PutMapping("/{id}")
    public ResponseEntity<RentalCarResponse> updateRentalCar(@PathVariable UUID id, @RequestBody RentalCarRequest rentalCarRequest){
        return ResponseEntity.ok(rentalCarService.updateRentalCar(id, rentalCarRequest));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRentalCar(@PathVariable UUID id){
        return ResponseEntity.ok(rentalCarService.deleteRentalCar(id));
    }
    @GetMapping("/models")
    public List<Model> getModelsByBrand(@RequestParam Brand brand) {
        return rentalCarService.getModelsByBrand(brand);
    }
}
