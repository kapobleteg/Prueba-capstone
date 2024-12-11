package org.centro.maibo.centromaibo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "psychologist")
@Getter
@Setter
public class Psychologist {

    @Id
    @SequenceGenerator(name="psychologist_id_seq", sequenceName = "psychologist_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "psychologist_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "psychologist")
    private List<Reservation> reservations;
}


