package com.imaee.propinq.controllers.implementations;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/demo")
@RestController
@Tag(
        name = "Ping",
        description = "Demo operations for environment configuration testing."
)
public class PingController {

    @GetMapping("/ping")
    @Operation(summary = "Returns 'pong' when called. Used to test logging and interactive documentation.")
    public String ping() {
        return "pong";
    }
}