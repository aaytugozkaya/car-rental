package com.aaytugozkaya.carrental.controller;

import com.aaytugozkaya.carrental.dto.request.AvailableCarRequest;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.aaytugozkaya.carrental.utils.AppConstant.BASE_URL;
import static com.aaytugozkaya.carrental.utils.AppConstant.PHOTO_DIRECTORY;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

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
    public ResponseEntity<RentalCarResponse> getCarById(@PathVariable String id){
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

    @PutMapping("/photo")
    public ResponseEntity<String> updatePhoto(@RequestParam("id") UUID id, @RequestParam("file") MultipartFile file){
        return ResponseEntity.ok(rentalCarService.uploadPhoto(id, file));
    }

    @GetMapping(value = "/photo/{fileName}" , produces = {IMAGE_PNG_VALUE,IMAGE_JPEG_VALUE})
    public ResponseEntity<byte[]> getPhoto(@PathVariable("fileName") String fileName) throws IOException {
        return ResponseEntity.ok(Files.readAllBytes(Paths.get(PHOTO_DIRECTORY + fileName)));
    }

    @PostMapping("/search")
    public ResponseEntity<List<RentalCarResponse>> searchCars(@RequestBody AvailableCarRequest availableCarRequest){
        return ResponseEntity.ok(rentalCarService.searchCars(availableCarRequest));
    }
}
