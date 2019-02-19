package com.jk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient//声明此项目是一个生产者
public class EsproducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EsproducerApplication.class, args);
    }

}
