package cn.gok.batisplus.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Admin implements Serializable {
    private   Integer aid;
    private  String adminName;
    private  String adminPassword;
    private Integer isEnable;
    private Integer isDelete;
}
