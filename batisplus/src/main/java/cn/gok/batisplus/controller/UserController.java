package cn.gok.batisplus.controller;

import cn.gok.batisplus.entity.User;
import cn.gok.batisplus.service.UserService;
import cn.gok.batisplus.utils.Result;
import com.alibaba.druid.sql.ast.statement.SQLColumnDefinition;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class UserController {
  @Autowired(required=false)
    private UserService userService;

//查询
@PostMapping("/user/findall")
    public Result findAll(){
        return Result.success("查询成功",userService.list(null));
}

//新增
@PostMapping("/user/add")
public Result addUser(@RequestBody User user){
    // 设置创建时间
    user.setCreateTime(new Date(System.currentTimeMillis()));
    if (userService.save(user) == true) {
        return Result.success("新增成功", null);
    } else {
        return Result.fail("新增失败");
    }
}

//修改
    @PostMapping("/user/update")
    public Result updateUser(@RequestBody User user){
        if (userService.updateById(user) == true) {
            return Result.success("修改成功",
                    null);
        } else {
            return Result.fail("修改失败");
        }
    }
//    删除
    @PostMapping("/user/delete")
    public Result delUser(@RequestBody JSONObject jsonParam) {
         int id= Integer.parseInt(jsonParam.get("id").toString());
        if (userService.removeById(id)==true) {
            return Result.success("删除成功", null);
        } else {
            return Result.fail("删除失败");
        }
    }
    //模糊查询
    @Autowired(required = false)
    private RedisTemplate redisTemplate;//为了调用redis存取数据的方法
    @PostMapping("/user/findlike")
    public Result findlike(@RequestBody JSONObject jsonParam) {
        System.out.println(jsonParam);
        String username=jsonParam.get("username").toString();
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("username",username);
        if (redisTemplate.hasKey("list")){
            redisTemplate.opsForList().leftPop("list");
            System.out.println("查询的是缓存");
        }else {
            redisTemplate.opsForList().leftPushAll("list",userService.list(queryWrapper));
            System.out.println("正在往缓存里加的list"+redisTemplate.opsForList().range("list",0,-1));
        }

            return Result.success("模糊查询成功",userService.list(queryWrapper));

    }

//分页查询
    @PostMapping("/user/getPage")
    public Result getUserList(@RequestBody JSONObject jsonParam){
        Integer pageNow = (Integer)jsonParam.get("pageNow");
        Integer pageSize = (Integer)jsonParam.get("pageSize");

        //调用分页助手
        Page<User>userPage=new Page<>(pageNow,pageSize);
        //条件构造器
        QueryWrapper<User>queryWrapper=new QueryWrapper<>();
        //        给定条件，为了获取用户列表
        //        username不为空
        queryWrapper.isNotNull("username");
        //        调用service的分页方法
//        分页助手，条件构造器(获取所有的用户)
        Page<User> listpage = (Page<User>) userService.page(userPage,queryWrapper);

        return Result.success("分页查询成功",listpage);
    }



//用户总数
    @PostMapping("/user/usernum")
    public Result usernum(){
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.isNotNull("username");
return Result.success("成功",userService.count(queryWrapper));
    }

    //统计各角色数量
    @PostMapping("/user/getsort")
    public Result getsort(){
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper
                .select("count(*) as value,user.role as name")
                .groupBy("role");
        return Result.success("查询成功",userService.listMaps(queryWrapper));

    }




}
