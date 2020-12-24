package com.altman.m78.controller;

import com.alibaba.excel.EasyExcel;
import com.altman.m78.model.DemoData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
public class TestController {

    @GetMapping("/upload")
    public void upload(){




    }



    @GetMapping("/download")
    public String downloadOfModel(){
        System.out.println("还不错");
        return "访问成功";
    }



    @GetMapping("/downloadModel")
    public void excludeOrIncludeWrite(HttpServletResponse response) throws IOException {

        //使用response流进行文件传输
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码
        String fileName = URLEncoder.encode("测试", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        // 根据用户传入字段 假设我们只要导出 name0,1,2,3
        Set<String> includeColumnFiledNames = new HashSet<String>();
        includeColumnFiledNames.add("name0");
        includeColumnFiledNames.add("name3");
        includeColumnFiledNames.add("name2");
        includeColumnFiledNames.add("name1");
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(response.getOutputStream(), DemoData.class).includeColumnFiledNames(includeColumnFiledNames).sheet("模板")
                .doWrite(data());
    }

    public List<DemoData> data(){
        ArrayList<DemoData> list = new ArrayList<>();
        DemoData demoData = new DemoData();
        demoData.setName0("第一格");
        demoData.setName2("第2格");
        demoData.setName3("第3格");
        demoData.setName4("第4格");
        demoData.setName5("第5格");
        list.add(demoData);
        return list;
    }


    @GetMapping("/delete")
    public String delete(Integer id) {
        boolean ids = deletePhone(id);
        if (id.equals(1)) {
            deletePhone(id);
            return "redirect:/list.do";
        } else {
            System.out.println("删除失败");
        }
        return "redirect:/list.do";
    }


    private boolean deletePhone(Integer id) {
        return true;
    }



}



