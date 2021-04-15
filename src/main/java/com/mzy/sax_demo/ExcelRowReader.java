package com.mzy.sax_demo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jack Miao
 * @date 2021/4/9 16:01
 * @desc
 */
@Data
public class ExcelRowReader implements IExcelRowReader {

    @Override
    public void getRows(int sheetIndex, int curRow, List<String> rowlist) {
        System.out.print(curRow+" ");
        System.out.println(rowlist);
        for (int i = 0; i < rowlist.size(); i++) {
            //System.out.print(rowlist.get(i).equals("") ? "*" :rowlist.get(i) + " ");
        }
        System.out.println(rowlist.size());
        System.out.println();
    }

}
