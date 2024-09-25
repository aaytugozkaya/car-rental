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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    private static final Logger logger = LogManager.getLogger(RentalCarController.class);


    @GetMapping("/allCars")
    public ResponseEntity<List<RentalCarResponse>> getAllCars(){
        logger.info("All cars are listed");
        return ResponseEntity.ok(rentalCarService.getAllCars());
    }
    @GetMapping("/{id}")
    public ResponseEntity<RentalCarResponse> getCarById(@PathVariable String id){
        logger.info("Car with id: " + id + " is listed");
        return ResponseEntity.ok(rentalCarService.getCarById(id));
    }
    @PostMapping
    public ResponseEntity<?> saveRentalCar(@RequestBody RentalCarRequest rentalCarRequest, BindingResult bindingResult){
        logger.info("Car saving attempt.");
        rentalCarValidator.validate(rentalCarRequest, bindingResult);
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = ErrorUtils.getErrorMessages(bindingResult);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("errors", errorMessages));
        }
        RentalCarResponse rentalCarResponse = rentalCarService.saveRentalCar(rentalCarRequest);
        logger.info("Car saved successfully with id %s", rentalCarResponse.getId().toString());
        return ResponseEntity.ok(rentalCarResponse);
    }
    @PutMapping("/{id}")
    public ResponseEntity<RentalCarResponse> updateRentalCar(@PathVariable UUID id, @RequestBody RentalCarRequest rentalCarRequest){
        RentalCarResponse rentalCarResponse = rentalCarService.updateRentalCar(id, rentalCarRequest);
        logger.info("Car with id: " + id + " is updated");
        return ResponseEntity.ok(rentalCarResponse);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRentalCar(@PathVariable UUID id){
        logger.info("Car with id: " + id + " is deleted");
        return ResponseEntity.ok(rentalCarService.deleteRentalCar(id));
    }
    @GetMapping("/models")
    public List<Model> getModelsByBrand(@RequestParam Brand brand) {
        return rentalCarService.getModelsByBrand(brand);
    }

    @PutMapping(value = "/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updatePhoto(@RequestParam("id") UUID id, @RequestParam("file") MultipartFile file) {
        logger.info("Photo is uploaded for car with id: " + id);
        return ResponseEntity.ok(rentalCarService.uploadPhoto(id, file));
    }



    @GetMapping(value = "/image/{fileName}" , produces = {IMAGE_PNG_VALUE,IMAGE_JPEG_VALUE})
    public ResponseEntity<byte[]> getPhoto(@PathVariable("fileName") String fileName) throws IOException {
        return ResponseEntity.ok(Files.readAllBytes(Paths.get(PHOTO_DIRECTORY + fileName)));
    }

    @PostMapping("/search")
    public ResponseEntity<List<RentalCarResponse>> searchCars(@RequestBody AvailableCarRequest availableCarRequest){
        logger.info("Car search attempt with parameters : %s , %s , %s", availableCarRequest.getLocation(), availableCarRequest.getStartDate(), availableCarRequest.getEndDate());
        return ResponseEntity.ok(rentalCarService.searchCars(availableCarRequest));
    }
}
