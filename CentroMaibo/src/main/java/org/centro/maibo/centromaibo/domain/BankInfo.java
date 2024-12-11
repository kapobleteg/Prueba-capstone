package org.centro.maibo.centromaibo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "bank_info")
@Getter
@Setter

public class BankInfo {
    @Id
    @SequenceGenerator(name="bank_info_id_seq", sequenceName = "bank_info_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "bank_info_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "destiny_name")
    private String name;

    @Column(name = "bank")
    private String bank;

    @Column(name = "rut")
    private String rut;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "amount_transfer")
    private Integer amountTransfer;

    @Column(name = "reason_transfer")
    private String reasonTransfer;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
