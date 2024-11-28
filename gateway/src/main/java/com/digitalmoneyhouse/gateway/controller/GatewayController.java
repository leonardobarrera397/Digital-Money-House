package com.digitalmoneyhouse.gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gateway")
@Slf4j
public class GatewayController {

    @GetMapping("/admin")
    @PreAuthorize("hasRole('admin-client')")
    public String admin() {

        return "Hola Admin";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('user-client') or hasRole('admin-client')")
    public String user() {

        return "Hola User";
    }

    @GetMapping("/hola")
    public String hola() {

        return "Hola";
    }
}
