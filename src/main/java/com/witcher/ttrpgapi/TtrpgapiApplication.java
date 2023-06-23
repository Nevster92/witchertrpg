package com.witcher.ttrpgapi;

import com.witcher.ttrpgapi.security.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class TtrpgapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TtrpgapiApplication.class, args);
	}

}
