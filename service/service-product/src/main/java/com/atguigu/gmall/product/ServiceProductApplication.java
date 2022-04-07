package com.atguigu.gmall.product;

import org.checkerframework.checker.units.qual.C;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.atguigu.gmall"})//组件扫描
@SpringBootApplication
@EnableDiscoveryClient//注册中心
public class ServiceProductApplication {
    public static void main(String[] args) {
SpringApplication.run(ServiceProductApplication.class, args);
    }
}
