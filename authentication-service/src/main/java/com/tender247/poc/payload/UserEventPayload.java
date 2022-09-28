package com.tender247.poc.payload;

import com.tender247.poc.enums.UserEventType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserEventPayload {

    private Long id;
    private String username;
    private String email;
    private String displayName;
    private UserEventType eventType;
}
