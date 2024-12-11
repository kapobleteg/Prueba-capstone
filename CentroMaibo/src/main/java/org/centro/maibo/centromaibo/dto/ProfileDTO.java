package org.centro.maibo.centromaibo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDTO {
    private Long id;
    private UserDTO user;
    private String specialty;
    private String[] certifications = new String[0];
}
