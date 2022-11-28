package cn.gok.batisplus.mapper;

import cn.gok.batisplus.entity.Shop;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

// 在对应的Mapper上面继承基本的类 BaseMapper
@Repository // 代表持久层
@Mapper
public interface ShopMapper extends BaseMapper<Shop> {
}
