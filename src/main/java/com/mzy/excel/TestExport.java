package com.mzy.excel;

import com.mzy.sax_demo.ExcelParserBigData;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
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
    public void importExcel() throws IOException, ExcelParserBigData.ParseException, InvalidFormatException {

        /*List<String[]> strings = XLSXCovertCSVReader.readerExcel(path, "1", 19);
        System.out.println(strings.size());*/
        ExcelParserBigData bigData = new ExcelParserBigData();
        FileInputStream inputStream = new FileInputStream("C:\\Users\\EDZ\\Desktop\\1.xlsx");
        ExcelParserBigData parse = bigData.parse(inputStream);
        List<String[]> datas = parse.getDatas();
        System.out.println();
        System.out.println(parse);
    }

    @RequestMapping("/importError")
    public void importError() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(path);
        SXSSFWorkbook sheets = new SXSSFWorkbook(workbook, 100, true, true);
        SXSSFSheet sheet = sheets.getSheet("1");
    }

    public static void main(String[] args) throws IOException, ExcelParserBigData.ParseException, InvalidFormatException {
        ExcelParserBigData bigData = new ExcelParserBigData();
        FileInputStream inputStream = new FileInputStream("C:\\Users\\EDZ\\Desktop\\1.xlsx");
        ExcelParserBigData parse = bigData.parse(inputStream);
        List<String[]> datas = parse.getDatas();
        datas.forEach(o ->{
            System.out.println(Arrays.toString(o));

            System.out.println(o.length);
        });
        System.out.println();
    }
}
