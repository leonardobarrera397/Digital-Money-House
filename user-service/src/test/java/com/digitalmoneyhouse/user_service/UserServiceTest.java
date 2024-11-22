package com.digitalmoneyhouse.user_service;

import com.digitalmoneyhouse.user_service.dto.UserRequestDTO;
import com.digitalmoneyhouse.user_service.dto.UserResponseDTO;
import com.digitalmoneyhouse.user_service.mapper.IUserMapper;
import com.digitalmoneyhouse.user_service.model.User;
import com.digitalmoneyhouse.user_service.repository.UserRepository;
import com.digitalmoneyhouse.user_service.service.impl.UserServiceImpl;
import com.digitalmoneyhouse.user_service.util.AliasGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private IUserMapper userMapper;

    @Mock
    private AliasGenerator aliasGenerator;

    private UserRequestDTO userRequestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Configurar un DTO de ejemplo para la creación de usuario
        userRequestDTO = new UserRequestDTO();
        userRequestDTO.setFirstName("John");
        userRequestDTO.setLastName("Doe");
        userRequestDTO.setDni("123123123");
        userRequestDTO.setEmail("john.doe@example.com");
        userRequestDTO.setPhone("123456789");
        userRequestDTO.setPassword("password123");
    }

    @Test
    void testRegisterUser() {
        // Simular la respuesta del generador de alias
        String generatedAlias = "river.stone.sky";
        when(aliasGenerator.generateAlias()).thenReturn(generatedAlias);

        // Crear un usuario de prueba
        User user = new User();
        user.setFirstName(userRequestDTO.getFirstName());
        user.setLastName(userRequestDTO.getLastName());
        user.setDni(userRequestDTO.getDni("123123123"));
        user.setEmail(userRequestDTO.getEmail());
        user.setPhone(userRequestDTO.getPhone());
        user.setPassword(userRequestDTO.getPassword());
        user.setAlias(generatedAlias);
        user.setCvu("1234567890123456789012"); // CVU simulado

        // Simular el comportamiento del repository y mapper
        when(userMapper.toEntity(userRequestDTO)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toResponseDTO(user)).thenReturn(new UserResponseDTO(user));

        // Ejecutar el método de registro de usuario
        UserResponseDTO response = userService.registerUser(userRequestDTO);

        // Verificar que el alias se haya generado correctamente
        assertNotNull(response);
        assertEquals(generatedAlias, response.getAlias());

        // Verificar que se haya llamado al repositorio para guardar el usuario
        verify(userRepository, times(1)).save(user);

        // Verificar que el alias haya sido asignado correctamente
        assertEquals(generatedAlias, user.getAlias());
    }
}
