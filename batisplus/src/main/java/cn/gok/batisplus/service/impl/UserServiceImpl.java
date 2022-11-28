package cn.gok.batisplus.service.impl;

import cn.gok.batisplus.entity.User;
import cn.gok.batisplus.mapper.UserMapper;
import cn.gok.batisplus.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {
}
