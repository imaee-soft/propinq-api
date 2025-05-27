package com.imaee.propinq.log.data;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document
@Builder
public class Log {

    @Id
    private final UUID id = UUID.randomUUID();
    private final LocalDateTime issuedAt = LocalDateTime.now();
    private String httpMethod;
    private String requestURI;
    private String browser;
    private String os;
    private String device;
}