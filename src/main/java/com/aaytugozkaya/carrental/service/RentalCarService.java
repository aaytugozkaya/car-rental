package com.aaytugozkaya.carrental.service;

import com.aaytugozkaya.carrental.dto.request.AvailableCarRequest;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.aaytugozkaya.carrental.utils.AppConstant.PHOTO_DIRECTORY;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
@RequiredArgsConstructor
public class RentalCarService {
    private static final Logger log = LoggerFactory.getLogger(RentalCarService.class);
    private final RentalCarRepository rentalCarRepository;

    public List<RentalCarResponse> getAllCars() {
        List<RentalCar> rentalCars = rentalCarRepository.findAll();
        return rentalCars.stream()
                .map(RentalCarMapper::toRentalCarResponse).collect(Collectors.toList());
    }

    public RentalCarResponse getCarById(String id) {

        RentalCar rentalCar = rentalCarRepository.findById(UUID.fromString(id)).orElseThrow(() -> new CarNotFoundException("Car not found"));
        return RentalCarMapper.toRentalCarResponse(rentalCar);
    }

    public RentalCarResponse saveRentalCar(RentalCarRequest rentalCarRequest) {
        try {
            return RentalCarMapper.toRentalCarResponse(rentalCarRepository.save(RentalCarMapper.toRentalCar(rentalCarRequest)));
        } catch (DataIntegrityViolationException ex) {
            throw new IllegalArgumentException("Car with the same plate already exists.");
        }

    }

    public RentalCarResponse updateRentalCar(UUID id, RentalCarRequest rentalCarRequest) {
        RentalCar rentalCar = rentalCarRepository.findById(id)
                .orElseThrow(() -> new CarNotFoundException("Car not found"));

        rentalCar.setBrand(rentalCarRequest.getBrand() != null ? rentalCarRequest.getBrand() : rentalCar.getBrand());
        rentalCar.setModel(rentalCarRequest.getModel() != null ? rentalCarRequest.getModel() : rentalCar.getModel());
        rentalCar.setYear(rentalCarRequest.getYear() != null ? rentalCarRequest.getYear() : rentalCar.getYear());
        rentalCar.setColor(rentalCarRequest.getColor() != null ? rentalCarRequest.getColor() : rentalCar.getColor());
        rentalCar.setPlate(rentalCarRequest.getPlate() != null ? rentalCarRequest.getPlate() : rentalCar.getPlate());
        rentalCar.setType(rentalCarRequest.getType() != null ? rentalCarRequest.getType() : rentalCar.getType());
        rentalCar.setFuelType(rentalCarRequest.getFuelType() != null ? rentalCarRequest.getFuelType() : rentalCar.getFuelType());
        rentalCar.setGearType(rentalCarRequest.getGearType() != null ? rentalCarRequest.getGearType() : rentalCar.getGearType());
        rentalCar.setKm(rentalCarRequest.getKm() != null ? rentalCarRequest.getKm() : rentalCar.getKm());
        rentalCar.setSeatCount(rentalCarRequest.getSeatCount() != null ? rentalCarRequest.getSeatCount() : rentalCar.getSeatCount());
        rentalCar.setDoorCount(rentalCarRequest.getDoorCount() != null ? rentalCarRequest.getDoorCount() : rentalCar.getDoorCount());
        rentalCar.setAirCondition(rentalCarRequest.getAirCondition() != null ? rentalCarRequest.getAirCondition() : rentalCar.getAirCondition());
        rentalCar.setDailyRentingPrice(rentalCarRequest.getDailyRentingPrice() != null ? rentalCarRequest.getDailyRentingPrice() : rentalCar.getDailyRentingPrice());
        rentalCar.setStatus(rentalCarRequest.getStatus() != null ? rentalCarRequest.getStatus() : rentalCar.getStatus());
        rentalCar.setDescription(rentalCarRequest.getDescription() != null ? rentalCarRequest.getDescription() : rentalCar.getDescription());
        rentalCar.setLocation(rentalCarRequest.getLocation() != null ? rentalCarRequest.getLocation() : rentalCar.getLocation());
        rentalCar.setDeposit(rentalCarRequest.getDeposit() != null ? rentalCarRequest.getDeposit() : rentalCar.getDeposit());
        rentalCar.setMinDriverLicenseYear(rentalCarRequest.getMinDriverLicenseYear() != null ? rentalCarRequest.getMinDriverLicenseYear() : rentalCar.getMinDriverLicenseYear());
        rentalCar.setDiscountRate(rentalCarRequest.getDiscountRate() != null ? rentalCarRequest.getDiscountRate() : rentalCar.getDiscountRate());

        return RentalCarMapper.toRentalCarResponse(rentalCarRepository.save(rentalCar));
    }


    public String deleteRentalCar(UUID id) {
        try {
            rentalCarRepository.deleteById(id);
            return "Car deleted";
        } catch (EntityNotFoundException e) {
            throw new CarNotFoundException("Car not found");
        }
    }

    public List<Model> getModelsByBrand(Brand brand) {
        return Stream.of(Model.values())
                .filter(carModel -> carModel.getBrand().name() == brand.name())
                .collect(Collectors.toList());
    }


    public List<RentalCarResponse> searchCars(AvailableCarRequest availableCarRequest) {
        return rentalCarRepository.findAvailableCars(availableCarRequest.getLocation(), availableCarRequest.getStartDate(), availableCarRequest.getEndDate()).stream().map(RentalCarMapper::toRentalCarResponse).collect(Collectors.toList());
    }

    public String uploadPhoto(UUID id, MultipartFile file) {
        log.info("Uploading photo for car with id: {}", id);
        RentalCar rentalCar = rentalCarRepository.findById(id).orElseThrow(() -> new CarNotFoundException("Car not found"));
        String photoUrl = photoFunction.apply(id, file);
        rentalCar.setImageUrl(photoUrl);
        rentalCarRepository.save(rentalCar);
        return photoUrl;
    }

    private final Function<String, String> fileExtension = filename -> Optional.of(filename).filter(name -> name.contains("."))
            .map(name -> "." + name.substring(filename.lastIndexOf(".") + 1)).orElse(".png");

    private final BiFunction<UUID, MultipartFile, String> photoFunction = (id, image) -> {
        String fileName = id + fileExtension.apply(image.getOriginalFilename());
        try {
            Path fileStorageLocation = Paths.get(PHOTO_DIRECTORY).toAbsolutePath().normalize();
            if (!Files.exists(fileStorageLocation)) {
                Files.createDirectories(fileStorageLocation);
            }
            Files.copy(image.getInputStream(), fileStorageLocation.resolve(fileName), REPLACE_EXISTING);
            return ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("api/v1/rental/image/" + fileName)
                    .toUriString();
        } catch (Exception e) {
            throw new RuntimeException("Error while uploading photo");
        }
    };
}
