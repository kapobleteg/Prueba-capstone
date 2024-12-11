package org.centro.maibo.centromaibo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReserveDTO {
    private String email;
    private String phone;
    private String name;
    private Long boxId;
    private Date reservationDate;
    private Date endDate;
    private String file;
}
