package com.mzy.excel;

import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * @author Jack Miao
 * @date 2021/4/1 16:08
 * @desc
 */
@RestController
public class TestExport {
    String path = "D:\\wx-file\\WeChat Files\\zhuoyang292861\\FileStorage\\File\\2021-04\\1.xlsx";
    @RequestMapping("/import")
    public void importExcel(){

        List<String[]> strings = XLSXCovertCSVReader.readerExcel(path, "1", 19);
        System.out.println(strings.size());
    }

    @RequestMapping("/importError")
    public void importError() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(path);
        SXSSFWorkbook sheets = new SXSSFWorkbook(workbook, 100, true, true);
        SXSSFSheet sheet = sheets.getSheet("1");
    }
}
