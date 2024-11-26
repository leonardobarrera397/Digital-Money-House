package com.digitalmoneyhouse.user_service.service;

import com.digitalmoneyhouse.user_service.dto.UserRequestDTO;
import com.digitalmoneyhouse.user_service.dto.UserResponseDTO;

public interface IUserService {
    UserResponseDTO registerUser(UserRequestDTO userRequestDTO);
}
