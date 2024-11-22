package com.digitalmoneyhouse.user_service.service.impl;

import com.digitalmoneyhouse.user_service.dto.UserRequestDTO;
import com.digitalmoneyhouse.user_service.dto.UserResponseDTO;
import com.digitalmoneyhouse.user_service.model.User;
import com.digitalmoneyhouse.user_service.repository.UserRepository;
import com.digitalmoneyhouse.user_service.service.IUserService;
import com.digitalmoneyhouse.user_service.util.AliasGenerator;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final AliasGenerator aliasGenerator;

    public UserServiceImpl(UserRepository userRepository, AliasGenerator aliasGenerator) {
        this.userRepository = userRepository;
        this.aliasGenerator = aliasGenerator;
    }

    @Override
    public UserResponseDTO registerUser(UserRequestDTO userRequestDTO) {
        // Crear la entidad User manualmente a partir del UserRequestDTO
        User user = new User();
        user.setFirstName(userRequestDTO.getFirstName());
        user.setLastName(userRequestDTO.getLastName());
        user.setEmail(userRequestDTO.getEmail());
        user.setPhone(userRequestDTO.getPhone());
        user.setPassword(userRequestDTO.getPassword());
        user.setCvu(generateCvu());
        user.setAlias(aliasGenerator.generateAlias());

        // Guardar el usuario en la base de datos
        User savedUser = userRepository.save(user);

        // Crear manualmente el UserResponseDTO a partir de la entidad User
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setId(savedUser.getId());
        responseDTO.setFirstName(savedUser.getFirstName());
        responseDTO.setLastName(savedUser.getLastName());
        responseDTO.setEmail(savedUser.getEmail());
        responseDTO.setPhone(savedUser.getPhone());
        responseDTO.setCvu(savedUser.getCvu());
        responseDTO.setAlias(savedUser.getAlias());

        return responseDTO;
    }

    private String generateCvu() {
        // Generar un CVU único de 22 dígitos
        return String.format("%022d", new Random().nextLong() & Long.MAX_VALUE);
    }
}