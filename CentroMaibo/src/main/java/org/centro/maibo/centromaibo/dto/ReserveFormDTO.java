package org.centro.maibo.centromaibo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReserveFormDTO {
    private String email;
    private String phone;
    private String name;
    private Long boxId;
    private Date reservationDate;
    private Date endDate;
    private String file;
    private String specialty;
    private List<String> certifications;
}
