package com.payu.pos.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import com.payu.discovery.register.config.DiscoveryServiceConfig;



@ComponentScan(basePackages = {"com.payu.pos.server", "com.payu.training.service"})
@Configuration
@EnableAutoConfiguration
@PropertySource("classpath:propertasy.properties")
@Import(DiscoveryServiceConfig.class)
public class PosServiceConfig {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(PosServiceConfig.class, args);
    }

}
