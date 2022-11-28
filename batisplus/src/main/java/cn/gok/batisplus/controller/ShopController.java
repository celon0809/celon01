package cn.gok.batisplus.controller;

import cn.gok.batisplus.entity.Shop;
import cn.gok.batisplus.entity.User;
import cn.gok.batisplus.service.ShopService;
import cn.gok.batisplus.utils.Result;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class ShopController {
    @Autowired(required=false)
    private ShopService shopService;

    //查询
    @PostMapping("/shop/findall")
    public Result findAll(){
        return Result.success("查询成功",shopService.list(null));
    }

    //新增
    @PostMapping("/shop/add")
    public Result add(@RequestBody Shop shop){
// 设置创建时间
        shop.setCreateTime(new Date(System.currentTimeMillis()));
        if (shopService.save(shop) == true) {
            return Result.success("新增成功", null);
        } else {
            return Result.fail("新增失败");
        }
    }

    //修改
    @PostMapping("/shop/update")
    public Result updateShop(@RequestBody Shop shop){
        if (shopService.updateById(shop) == true) {
            return Result.success("修改成功",
                    null);
        } else {
            return Result.fail("修改失败");
        }
    }
    //    删除
    @PostMapping("/shop/delete")
    public Result delshop(@RequestBody JSONObject jsonParam) {
        int sid= Integer.parseInt(jsonParam.get("sid").toString());
        if (shopService.removeById(sid)==true) {
            return Result.success("删除成功", null);
        } else {
            return Result.fail("删除失败");
        }
    }
    @PostMapping ("/shop/getPage")
    public Result getshopList(@RequestBody JSONObject jsonParam) {
        Integer pageNow = (Integer)jsonParam.get("pageNow");
        Integer pageSize = (Integer)jsonParam.get("pageSize");
        Page<Shop> shopPage=new Page<>(pageNow,pageSize);
        QueryWrapper<Shop> queryWrapper=new QueryWrapper<>();
        queryWrapper.isNotNull("sname");
        Page<Shop> listpage = (Page<Shop>) shopService.page(shopPage,queryWrapper);

        return Result.success("分页查询成功",listpage);
    }

    //模糊查询
    @Autowired(required = false)
    private RedisTemplate redisTemplate;//为了调用redis存取数据的方法
    @PostMapping("/shop/findlike")
    public Result findlike(@RequestBody JSONObject jsonParam){
        String sname=jsonParam.get("sname").toString();
        QueryWrapper<Shop> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("sname",sname);
        if (redisTemplate.hasKey("list")){
            redisTemplate.opsForList().leftPop("list");
            System.out.println("查询的是缓存");
        }else {
            redisTemplate.opsForList().leftPushAll("list",shopService.list(queryWrapper));
            System.out.println("正在往缓存里加的list"+redisTemplate.opsForList().range("list",0,-1));
        }
        return Result.success("模糊查询成功",shopService.list(queryWrapper));
    }

    //商品分类
    @PostMapping("/shop/shopnum")
    public Result fenlei(){
        QueryWrapper<Shop> queryWrapper=new QueryWrapper<>();
        queryWrapper.select("count(*)").groupBy("sclassify");
        return Result.success("商品种类数量",shopService.list(queryWrapper).size());
    }

    //商品销量
    @PostMapping("/shop/addsum")
    public Result sum(){
        QueryWrapper<Shop> queryWrapper=new QueryWrapper<>();
        queryWrapper.select("sum(sale)");
        return Result.success("销量",shopService.getMap(queryWrapper).get("sum(sale)"));
    }
    //商品库存
    @PostMapping("/shop/kucun")
    public Result kucun(){
        QueryWrapper<Shop> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("sum(sinventory)");
        return Result.success("库存",shopService.getMap(queryWrapper).get("sum(sinventory)"));

    }

    //统计各商品数量
    @PostMapping("/shop/getsort")
    public Result getsort(){
        QueryWrapper<Shop> queryWrapper=new QueryWrapper<>();
        queryWrapper
                .select("count(*) as value,shop.sclassify as name")
                .groupBy("sclassify");
        return Result.success("查询成功",shopService.listMaps(queryWrapper));

    }

}
