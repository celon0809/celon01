package cn.gok.batisplus.controller;

import cn.gok.batisplus.entity.Admin;
import cn.gok.batisplus.entity.User;
import cn.gok.batisplus.service.AdminService;
import cn.gok.batisplus.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;
    @PostMapping("/admin/login")
    public Result login(@RequestBody Admin admin){
        if (adminService.login(admin)==true){
            return Result.success("登陆成功",null);
        }else return Result.fail("登陆失败");
    }
    //新增注册
@PostMapping("/admin/register")
    public Result register(@RequestBody Admin admin){
        if (adminService.register(admin)==true) {
            return Result.success("注册成功", null);
        } else {
            return Result.fail("注册失败");
        }
    }
}
