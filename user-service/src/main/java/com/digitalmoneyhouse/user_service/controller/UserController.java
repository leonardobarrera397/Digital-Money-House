package com.digitalmoneyhouse.user_service.controller;

import com.digitalmoneyhouse.user_service.dto.UserRequestDTO;
import com.digitalmoneyhouse.user_service.dto.UserResponseDTO;
import com.digitalmoneyhouse.user_service.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        try {
            UserResponseDTO registeredUser = userService.registerUser(userRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/prueba")
    public ResponseEntity<UserResponseDTO> prueba(@RequestHeader("Origen") String origen) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(1L);
        userResponseDTO.setDni("12345678");
        userResponseDTO.setFirstName("instancia 1");
        userResponseDTO.setLastName("Argento");
        userResponseDTO.setEmail("pepe@email.com");
        userResponseDTO.setPhone("123456789");
        userResponseDTO.setCvu("0001234567890987654321");
        userResponseDTO.setAlias("uno.dos.tres");

        System.out.println("Enviado desde " + origen);

        if (!origen.equals("gateway")){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(userResponseDTO);
        }


        return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('user-client') or hasRole('admin-client')")
    public String user() {

        return "Hola User desde User-service";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('admin-client')")
    public String admin() {

        return "Hola Admin desde User-service";
    }

    @GetMapping("/me")
    public Map<String, Object> getCurrentUser(Authentication authentication) {
        Map<String, Object> details = new HashMap<>();
        details.put("name", authentication.getName());
        details.put("authorities", authentication.getAuthorities());
        return details;
    }




}
