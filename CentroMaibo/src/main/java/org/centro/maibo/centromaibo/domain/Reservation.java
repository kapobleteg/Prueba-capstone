package org.centro.maibo.centromaibo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "reservation")
@Getter
@Setter
public class Reservation {

    @Id
    @SequenceGenerator(name = "reservation_id_seq", sequenceName = "reservation_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservation_id_seq")
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @JoinColumn(name = "box_id")
    @ManyToOne
    private Box box;

    @Column(name = "startDate")
    private Date startDate;

    @Column(name = "endDate")
    private Date endDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatesEnum status;

    @JoinColumn(name = "receipt_id")
    @ManyToOne
    private PaymentReceipt paymentReceipt;

    @JoinColumn(name = "psychologist_id")
    @ManyToOne
    private Psychologist psychologist;


}
