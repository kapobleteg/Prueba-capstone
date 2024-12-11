package org.centro.maibo.centromaibo;

import org.centro.maibo.centromaibo.properties.MailProperties;
import org.centro.maibo.centromaibo.properties.RoleProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({MailProperties.class, RoleProperties.class})
public class CentroMaiboApplication {

	public static void main(String[] args) {
		SpringApplication.run(CentroMaiboApplication.class, args);
	}

}
