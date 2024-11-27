package com.digitalmoneyhouse.user_service.controller;

import com.digitalmoneyhouse.user_service.dto.UserRequestDTO;
import com.digitalmoneyhouse.user_service.dto.UserResponseDTO;
import com.digitalmoneyhouse.user_service.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


}
