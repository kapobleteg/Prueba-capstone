package org.centro.maibo.centromaibo.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "maibo.mail")
@Data
public class MailProperties {
    private String from;
    private String baseUrl;
}
