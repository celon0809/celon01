package cn.gok.batisplus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;

public class AutoCode {
    public static void main(String[] args) {
        // 1、创建代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 2、全局配置
        GlobalConfig gc = new GlobalConfig();
//得到当前文件夹的路径
        String projectPath = System.getProperty("user.dir");
// 代码输出目录，这里将代码输出到src/main/java目录下
        gc.setOutputDir(projectPath+"/src/main/java");
//代码生成的作者
        gc.setAuthor("ccc");
//生成后是否打开资源管理器（生成之后文件是否展开成树结构）
        gc.setOpen(false);
//重新生成时文件是否覆盖
        gc.setFileOverride(false);
// 去Service的I前缀
        gc.setServiceName("%sService");
//主键策略(主键如果是long类型，则是IdType.ID_WORKER。如果是char类型，则是ID_WORKER_STR)
        gc.setIdType(IdType.ID_WORKER);
//定义生成的实体类中日期类型
        gc.setDateType(DateType.ONLY_DATE);
//开启Swagger2模式,swagger就是一款让你更好的书写API文档的框架。
        gc.setSwagger2(false);
        mpg.setGlobalConfig(gc);
//3、设置数据源
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/abc?useSSL=false&useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8");
                dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
//数据库类型
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);
//3、包的配置
        PackageConfig pc = new PackageConfig();
//设置父包
        pc.setParent("cn.gok.batisplus");
//可生成模块，也相当于包
// pc.setModuleName();
// 设置子包
        pc.setEntity("entity");
        pc.setMapper("mapper");
        pc.setService("service");
        pc.setController("controller");
        mpg.setPackageInfo(pc);
//4、策略配置，根据表生成代码
        StrategyConfig strategy = new StrategyConfig();
// 根据表的名称生成相应代码
        strategy.setInclude("student"); // 设置要映射的表名
        strategy.setNaming(NamingStrategy.underline_to_camel);
//数据库表映射到实体的命名策略，下划线转驼峰式命名
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
// 自动lombok；
        strategy.setEntityLombokModel(true);
//设置逻辑删除
        strategy.setLogicDeleteFieldName("delted");
// 自动填充配置
        TableFill gmtCreate = new TableFill("create_time", FieldFill.INSERT);
        TableFill gmtModified = new TableFill("update_time", FieldFill.INSERT_UPDATE);
        ArrayList<TableFill> tableFills = new ArrayList<>();
        tableFills.add(gmtCreate);
        tableFills.add(gmtModified);
        strategy.setTableFillList(tableFills);
// 乐观锁
        strategy.setVersionFieldName("version");
//restful风格的controller开启
        strategy.setRestControllerStyle(true);
// strategy.setControllerMappingHyphenStyle(true); //localhost:8080/hello_id_2
        mpg.setStrategy(strategy);
//执行
        mpg.execute();
    }
}
