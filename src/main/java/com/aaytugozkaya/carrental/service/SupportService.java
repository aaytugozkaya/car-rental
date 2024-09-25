package com.aaytugozkaya.carrental.service;

import com.aaytugozkaya.carrental.dto.response.SupportResponse;
import com.aaytugozkaya.carrental.entity.enums.Status;
import com.aaytugozkaya.carrental.exception.SupportNotFoundException;
import com.aaytugozkaya.carrental.mapper.SupportMapper;
import com.aaytugozkaya.carrental.repository.SupportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SupportService {
    private final SupportRepository supportRepository;

    public List<SupportResponse> getAllSupports() {
        return supportRepository.findAll().stream().map(SupportMapper::toSupportResponse).toList();
    }

    public SupportResponse getSupport(UUID id) {
        return SupportMapper.toSupportResponse(supportRepository.findById(id).orElseThrow(() -> new SupportNotFoundException("Support not found")));
    }

    public SupportResponse saveSupport(SupportResponse support) {
        return SupportMapper.toSupportResponse(supportRepository.save(SupportMapper.toSupport(support)));
    }

    public SupportResponse updateSupport(UUID id, SupportResponse updatedSupport) {
        return SupportMapper.toSupportResponse(supportRepository.save(SupportMapper.toSupport(updatedSupport)));
    }

    public String deleteSupport(UUID id) {
        supportRepository.deleteById(id);
        return "Support with id: " + id + " deleted successfully";
    }
}
