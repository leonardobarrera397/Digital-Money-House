package com.digitalmoneyhouse.user_service.service;

import com.digitalmoneyhouse.user_service.dto.UserRequestDTO;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public interface IKeycloackService {
    
    List<UserRepresentation> findAllUsers();
    List<UserRepresentation> searchUserByUsername(String username);
    String createUser(UserRequestDTO userRequestDTO);
    void deleteUser(String userId);
    void updateUser(String userId, UserRequestDTO userRequestDTO);
}
