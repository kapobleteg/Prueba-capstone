package org.centro.maibo.centromaibo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JWTToken {
    @JsonProperty("id_token")
    private String token;
}