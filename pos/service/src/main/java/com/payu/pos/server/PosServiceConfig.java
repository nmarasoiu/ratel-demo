package com.payu.pos.server;

import com.payu.ratel.config.ServiceDiscoveryConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;



@ComponentScan(basePackages = {"com.payu.pos.server", "com.payu.training.service", "com.payu.ratel"})
@Configuration
@EnableAutoConfiguration
@Import(ServiceDiscoveryConfig.class)
public class PosServiceConfig {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(PosServiceConfig.class, args);
    }

}
