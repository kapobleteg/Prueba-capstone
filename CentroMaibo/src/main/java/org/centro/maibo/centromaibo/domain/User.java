package org.centro.maibo.centromaibo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User {

    @Id
    @SequenceGenerator(name="user_id_seq", sequenceName = "user_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "user_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password_hash")
    private String password;

    @Column(name = "phone")
    private String phone;

    @JoinColumn(name="role_id")
    @ManyToOne
    private Role role;

    @Column(name = "status")
    private boolean status;

    @Column(name = "activation_key")
    private String activationKey;

}


