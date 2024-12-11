package org.centro.maibo.centromaibo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.centro.maibo.centromaibo.domain.Box;
import org.centro.maibo.centromaibo.domain.StatesEnum;
import org.centro.maibo.centromaibo.domain.User;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {
    private Long id;
    private User user;
    private Box box;
    private Date startDate;
    private Date endDate;
    private StatesEnum status;
    private PaymentReceiptDTO paymentReceipt;
    private PsychologistDTO psychologist;
}
