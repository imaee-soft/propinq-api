package com.imaee.propinq.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
    info = @Info(
                title = "PropInq",
                description = "Platform that streamlines property rental management and communication between owners and tenants.",
                version = "0.0.1"
    ),
    servers = {
            @Server(
                description = "LOCAL ENV",
                url = "http://localhost:8080/"
            )
    }
)
public class OpenAPIConfig {}