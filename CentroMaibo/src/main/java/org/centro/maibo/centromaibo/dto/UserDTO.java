package org.centro.maibo.centromaibo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private RoleDTO role;
    private boolean status;
    private String activationKey;

}
