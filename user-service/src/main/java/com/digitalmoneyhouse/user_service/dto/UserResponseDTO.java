package com.digitalmoneyhouse.user_service.dto;

import com.digitalmoneyhouse.user_service.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String dni;
    private String email;
    private String phone;
    private String cvu;
    private String alias;

    public UserResponseDTO(User user) {
    }

}
