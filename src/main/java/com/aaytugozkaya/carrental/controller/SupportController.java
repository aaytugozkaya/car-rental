package com.aaytugozkaya.carrental.controller;

import com.aaytugozkaya.carrental.dto.response.SupportResponse;
import com.aaytugozkaya.carrental.service.SupportService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.aaytugozkaya.carrental.utils.AppConstant.BASE_URL;

@RestController
@RequestMapping(BASE_URL + "/support")
@RequiredArgsConstructor
public class SupportController {
    private final SupportService supportService;
    private static final Logger logger = LogManager.getLogger(SupportController.class);
    @GetMapping
    public ResponseEntity<List<SupportResponse>> getAllSupports() {
        logger.info("All supports are listed");
        return ResponseEntity.ok(supportService.getAllSupports());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupportResponse> getSupportById(@PathVariable UUID id) {
        logger.info("Support with id: " + id + " is listed");
        return ResponseEntity.ok(supportService.getSupport(id));
    }

    @PostMapping
    public ResponseEntity<SupportResponse> createSupport(@RequestBody SupportResponse support) {
        SupportResponse response = supportService.saveSupport(support);
        logger.info("Support saving attempt with id : %s", response.getId().toString());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupportResponse> updateSupport(@PathVariable UUID id, @RequestBody SupportResponse updatedSupport) {
        SupportResponse response = supportService.updateSupport(id, updatedSupport);
        logger.info("Support with id: %s is updated", id);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSupport(@PathVariable UUID id) {
        logger.info("Support with id: %s is deleted", id);
        return ResponseEntity.ok(supportService.deleteSupport(id));
    }

}
