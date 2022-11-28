package cn.gok.batisplus;

import javafx.application.Application;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("cn.gok.batisplus.mapper")
@SpringBootApplication
public class BatisplusApplication{

    public static void main(String[] args) {
        SpringApplication.run(BatisplusApplication.class, args);
    }
}
