package org.centro.maibo.centromaibo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookAppointmentDTO {
    private String email;
    private String phone;
    private String name;
    private Date reservationDate;
    private Date endDate;
    private String file;
    private Integer price;
    private PsychologistDTO psychologist;
}
