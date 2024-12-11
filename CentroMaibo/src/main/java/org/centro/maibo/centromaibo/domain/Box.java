package org.centro.maibo.centromaibo.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "box")
@Getter
@Setter
public class Box {
    @Id
    @SequenceGenerator(name = "box_id_seq", sequenceName = "box_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "box_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "availability")
    private Boolean availability;

    @Column(name = "price")
    private Integer price;

    @Column(name = "time")
    private BigDecimal time;

    @ElementCollection
    @CollectionTable(
            name = "box_image_urls",
            joinColumns = @JoinColumn(name = "box_id")
    )
    @Column(name = "image_urls")
    private List<String> imageUrls;
}

