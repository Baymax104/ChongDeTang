package com.cdtde.chongdetang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class ChongdetangApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChongdetangApplication.class, args);
    }

}
