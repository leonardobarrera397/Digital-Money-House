package com.digitalmoneyhouse.user_service.service.impl;

import com.digitalmoneyhouse.user_service.dto.UserRequestDTO;
import com.digitalmoneyhouse.user_service.dto.UserResponseDTO;
import com.digitalmoneyhouse.user_service.mapper.IUserMapper;
import com.digitalmoneyhouse.user_service.model.User;
import com.digitalmoneyhouse.user_service.repository.UserRepository;
import com.digitalmoneyhouse.user_service.service.IUserService;
import com.digitalmoneyhouse.user_service.util.AliasGenerator;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final IUserMapper userMapper;
    private final AliasGenerator aliasGenerator;

    public UserServiceImpl(UserRepository userRepository, IUserMapper userMapper, AliasGenerator aliasGenerator) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.aliasGenerator = aliasGenerator;
    }

    @Override
    public UserResponseDTO registerUser(UserRequestDTO userRequestDTO) {

        User user = userMapper.toEntity(userRequestDTO);
        user.setCvu(generateCvu());
        user.setAlias(aliasGenerator.generateAlias());


        User savedUser = userRepository.save(user);
        UserResponseDTO respuesta = userMapper.toResponseDTO(savedUser);

        return userMapper.toResponseDTO(savedUser);
    }

    private String generateCvu() {
        return String.format("%022d", new Random().nextInt(1000000000));
    }
}