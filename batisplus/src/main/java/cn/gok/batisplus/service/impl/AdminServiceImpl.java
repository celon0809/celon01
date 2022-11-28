package cn.gok.batisplus.service.impl;

import cn.gok.batisplus.entity.Admin;
import cn.gok.batisplus.mapper.AdminMapper;
import cn.gok.batisplus.service.AdminService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional()
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Autowired(required = false)
    private RedisTemplate redisTemplate;
    @Autowired(required = false)
    private AdminMapper adminMapper;
    @Override
    public boolean login(Admin admin) {
        QueryWrapper<Admin> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("admin_name",admin.getAdminName());
        queryWrapper.eq("admin_password",admin.getAdminPassword());
        Admin one=null;
        if (redisTemplate.hasKey(admin.getAdminName())){
            one=(Admin)redisTemplate.opsForHash().get(admin.getAdminName(),admin.getAdminName());
            System.out.println("查询缓存");
            if (one.getAdminName().equals(admin.getAdminName())&&one.getAdminPassword().equals(admin.getAdminPassword())){
                return true;
            }else return false;
        }else {
            one=getOne(queryWrapper);
        }
        if (one!=null){
            redisTemplate.opsForHash().put(admin.getAdminName(),admin.getAdminName(),one);
            System.out.println("查询的是数据库");
            return true;
        }else return false;
    }


    @Override
    public boolean register(Admin admin) {
        QueryWrapper<Admin> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("admin_name",admin.getAdminName());
        Admin one=getOne(queryWrapper);
        if (one!=null){
//            已经存在此用户，不能注册
            return  false;
        }else {//此用户不存在，可以进行注册
            adminMapper.insert(admin);
            return true;
        }
    }

}
