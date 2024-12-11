package org.centro.maibo.centromaibo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoxDTO {
    private Long id;
    private String name;
    private String location;
    private Integer capacity;
    private Boolean availability;
    private Integer price;
    private BigDecimal time;
    private List<String> imageURLS;
}
