package org.centro.maibo.centromaibo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankInfoDTO {
    private Long id;
    private String name;
    private String bank;
    private String rut;
    private String accountType;
    private String accountNumber;
    private Integer amountTransfer;
    private String reasonTransfer;
    private UserDTO user;

}
