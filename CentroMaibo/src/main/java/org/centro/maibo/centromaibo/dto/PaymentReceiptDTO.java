package org.centro.maibo.centromaibo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentReceiptDTO {
    private Long id;
    private String attached;
    private BigDecimal amount;
    private Date date;
    private Boolean status;
    private String comment;
}
