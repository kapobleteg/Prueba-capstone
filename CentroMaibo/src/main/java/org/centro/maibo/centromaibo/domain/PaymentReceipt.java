package org.centro.maibo.centromaibo.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "payment_receipt")
@Getter
@Setter
public class PaymentReceipt {
    @Id
    @SequenceGenerator(name = "box_id_seq", sequenceName = "box_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "box_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "attached")
    private String attached;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "date")
    private Date date;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatesEnum status;

    @Column(name = "comment")
    private String comment;
}
