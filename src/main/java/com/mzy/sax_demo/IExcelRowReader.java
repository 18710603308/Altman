package com.mzy.sax_demo;

import java.util.List;

/**
 * @author Jack Miao
 * @date 2021/4/9 16:00
 * @desc
 */
public interface IExcelRowReader {

    /**
     * 业务逻辑实现方法
     *
     * @param sheetIndex
     * @param curRow
     * @param rowlist
     */
    void getRows(int sheetIndex, int curRow, List<String> rowlist);
}
