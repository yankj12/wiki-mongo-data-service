package com.cowork.wikidata.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@EnableDiscoveryClient
@Configuration
@ComponentScan("com.cowork")
@EnableAutoConfiguration
@SpringBootApplication
public class WikiDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(WikiDataApplication.class, args);
    }
}
