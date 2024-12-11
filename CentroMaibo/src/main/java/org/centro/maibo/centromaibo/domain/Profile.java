package org.centro.maibo.centromaibo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "profile")
@Getter
@Setter
public class Profile {

    @Id
    @SequenceGenerator(name = "profile_id_seq", sequenceName = "profile_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_id_seq")
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @Column(name = "specialty")
    private String specialty;

    @Column(name = "certifications", columnDefinition = "text[]")
    @JdbcTypeCode(SqlTypes.ARRAY)
    private String[] certifications = new String[0];

}
