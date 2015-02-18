package com.payu.transaction.server;


import com.payu.ratel.config.ServiceDiscoveryConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@ComponentScan(basePackages = {"com.payu.transaction.server", "com.payu.training"})
@Configuration
@EnableAutoConfiguration
@Import(ServiceDiscoveryConfig.class)
public class TransactionServiceConfig {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(TransactionServiceConfig.class, args);
    }

}
