package com.qiuhua.kkk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.qiuhua.kkk.Mapper")
public class KkkApplication {

    public static void main(String[] args) {
        SpringApplication.run(KkkApplication.class, args);
    }

}
