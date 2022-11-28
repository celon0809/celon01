package cn.gok.batisplus.service.impl;

import cn.gok.batisplus.entity.Shop;
import cn.gok.batisplus.mapper.ShopMapper;
import cn.gok.batisplus.service.ShopService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements ShopService {
}
