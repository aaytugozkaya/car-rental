package com.aaytugozkaya.carrental.controller;

import com.aaytugozkaya.carrental.dto.response.SupportResponse;
import com.aaytugozkaya.carrental.service.SupportService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping
    public ResponseEntity<List<SupportResponse>> getAllSupports() {
        return ResponseEntity.ok(supportService.getAllSupports());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupportResponse> getAllSupports(@PathVariable UUID id) {
        return ResponseEntity.ok(supportService.getSupport(id));
    }

    @PostMapping
    public ResponseEntity<SupportResponse> createSupport(@RequestBody SupportResponse support) {
        return ResponseEntity.ok(supportService.saveSupport(support));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupportResponse> updateSupport(@PathVariable UUID id, @RequestBody SupportResponse updatedSupport) {
        return ResponseEntity.ok(supportService.updateSupport(id, updatedSupport));
    }
}
