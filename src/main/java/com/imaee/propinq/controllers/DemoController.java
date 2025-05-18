package com.imaee.propinq.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController
public class DemoController {

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}