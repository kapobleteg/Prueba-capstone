package org.centro.maibo.centromaibo.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "maibo.role")
@Data
public class RoleProperties {
    private Long clientRoleId;
    private Long adminRoleId;
    private Long patientRoleId;
}
