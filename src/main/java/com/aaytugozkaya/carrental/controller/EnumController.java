package com.aaytugozkaya.carrental.controller;

import com.aaytugozkaya.carrental.entity.enums.Brand;
import com.aaytugozkaya.carrental.entity.enums.CarLocation;
import com.aaytugozkaya.carrental.entity.enums.CarType;
import com.aaytugozkaya.carrental.entity.enums.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.aaytugozkaya.carrental.utils.AppConstant.BASE_URL;

@RestController
@RequestMapping(BASE_URL + "/enum")
@RequiredArgsConstructor
public class EnumController {

    @GetMapping("/location")
    public List<String> getCarLocations() {
        return Arrays.stream(CarLocation.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @GetMapping("/brands")
    public List<String> getBrands() {
        return Arrays.stream(Brand.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @GetMapping("/models/{brand}")
    public List<String> getModelsByBrand(@PathVariable Brand brand) {
        return  Stream.of(Model.values())
                .filter(carModel -> carModel.getBrand().name().equals(brand.name()))
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @GetMapping("/type")
    public List<String> getType() {
        return Arrays.stream(CarType.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

}
