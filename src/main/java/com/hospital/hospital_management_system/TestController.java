package com.hospital.hospital_management_system;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public String sayHello() {

        return "Hospital Management System Backend is running!";
    }
}