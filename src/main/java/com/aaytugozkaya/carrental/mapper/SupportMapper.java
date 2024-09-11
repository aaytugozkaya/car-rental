package com.aaytugozkaya.carrental.mapper;

import com.aaytugozkaya.carrental.dto.response.SupportResponse;
import com.aaytugozkaya.carrental.entity.Support;

public class SupportMapper {
    public static SupportResponse toSupportResponse(Support support) {
        return SupportResponse.builder()
                .id(support.getId())
                .name(support.getName())
                .email(support.getEmail())
                .message(support.getMessage())
                .build();
    }

    public static Support toSupport(SupportResponse supportResponse) {
        return Support.builder()
                .id(supportResponse.getId())
                .name(supportResponse.getName())
                .email(supportResponse.getEmail())
                .message(supportResponse.getMessage())
                .build();
    }
}
