package cn.gok.batisplus.service;

import cn.gok.batisplus.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;

public interface AdminService extends IService<Admin> {
    public boolean login(Admin admin);
    public boolean register(Admin admin);
}
