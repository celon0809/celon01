package cn.gok.batisplus.controller;

import cn.gok.batisplus.entity.Shop;
import cn.gok.batisplus.service.ShopService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
public class Excel2Controller {
    @Autowired
    private ShopService shopService;
    //excel导出
    @GetMapping("/excel/exportExcel2")
    public void exportExcel2(HttpServletResponse response) throws Exception {
        //查询所有数据
        List<Shop> list = shopService.list(null);
        System.out.println(list);
        //在内存操作，写出到浏览器，从浏览器下载
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题名
        writer.addHeaderAlias("sid", "SID");
        writer.addHeaderAlias("simage", "商品图片");
        writer.addHeaderAlias("sname", "商品名称");
        writer.addHeaderAlias("sinventory", "商品库存");
        writer.addHeaderAlias("sclassify", "商品分类");
        writer.addHeaderAlias("sprice", "商品价格");
        writer.addHeaderAlias("sale", "商品销量");
        writer.addHeaderAlias("sintroduce", "商品介绍");
        writer.addHeaderAlias("createTime", "创建时间");
        writer.addHeaderAlias("updateTime", "更新时间");
        //一次性写出list内的对象到excel，使用默认格式，强制输出标题
        writer.write(list,true);

        //设置浏览器响应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("商品信息","UTF-8");
        response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");

        ServletOutputStream outputStream = response.getOutputStream();
        writer.flush(outputStream,true);

        //关闭流
        outputStream.close();
        writer.close();
    }

    //excel导入
    @PostMapping("/excel/importExcel2")
    public Boolean importExcel2(MultipartFile file) throws IOException, ParseException {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);

        //方式1：通过JavaBean的方式读取excel内的对象，但是要求表头必须市英文，和JavaBean属性对应
//        List<User> users = reader.readAll(User.class);

        //方式二：忽略表头中文，直接获取表格数据
        List<List<Object>> list = reader.read(0);//从哪一列开始读取，0代表第一列
        List<Shop> shops = CollUtil.newArrayList();

        for(List<Object> row:list){
            Shop shop = new Shop();
            System.out.println(row);
//            user.setId(Integer.valueOf(row.get(0).toString()));
            shop.setSimage(row.get(0).toString());
//            System.out.println(row.get(2).toString());
            shop.setSname(row.get(1).toString());
            shop.setSinventory(Integer.valueOf(row.get(2).toString()));
            shop.setSclassify(row.get(3).toString());
            shop.setSprice(Double.valueOf(row.get(4).toString()));
            shop.setSintroduce(row.get(5).toString());
//            System.out.println(row.get(5).toString());
            shop.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(row.get(6).toString()));
            shops.add(shop);
        }

        //将excel导入的数据保存到数据库
        shopService.saveBatch(shops);
        return true;
    }
}
