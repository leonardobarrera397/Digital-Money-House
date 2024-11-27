package com.digitalmoneyhouse.user_service.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    Long id;
    String firstName;
    String lastName;
    String dni;
    String email;
    String phone;
    String cvu;
    String alias;


}
