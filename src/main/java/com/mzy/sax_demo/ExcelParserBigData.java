package com.mzy.sax_demo;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jimmy Shan
 * @date 2021-04-09
 * @desc 解析大数据量excel文件，避免OOM发生
 */
@Component
public class ExcelParserBigData {
    private static final Logger logger = LoggerFactory.getLogger(ExcelParserBigData.class);
    private ISheetContentHandler contentHandler = new DefaultSheetHandler(); //表格默认处理器
    private List<String[]> datas = new ArrayList<String[]>(); //读取数据

    /**
     * @desc 转换表格,默认为转换第一个表格
     */
    public ExcelParserBigData parse(InputStream stream)
            throws InvalidFormatException, IOException, ParseException {
        return parse(stream, 1);
    }

    /**
     * @desc 解析方法
     */
    public synchronized ExcelParserBigData parse(InputStream stream, int sheetId)
            throws InvalidFormatException, IOException, ParseException {
        // 每次转换前都清空数据
        datas.clear();
        // 打开表格文件输入流
        OPCPackage pkg = OPCPackage.open(stream);
        try {
            // 创建表阅读器
            XSSFReader reader;
            try {
                reader = new XSSFReader(pkg);
            } catch (OpenXML4JException e) {
                logger.error("读取表格出错");
                throw new ParseException(e.fillInStackTrace());
            }

            // 转换指定单元表
            InputStream shellStream = reader.getSheet("rId" + sheetId);
            try {
                InputSource sheetSource = new InputSource(shellStream);
                StylesTable styles = reader.getStylesTable();
                ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(pkg);
                getContentHandler().init(datas);// 设置读取出的数据
                // 获取转换器
                XMLReader parser = getSheetParser(styles, strings);
                parser.parse(sheetSource);
            } catch (SAXException e) {
                logger.error("读取表格出错");
                throw new ParseException(e.fillInStackTrace());
            } finally {
                shellStream.close();
            }
        } finally {
            pkg.close();
        }

        return this;
    }

    /**
     * @desc 获取表格读取数据,获取数据前，需要先转换数据，此方法不会获取第一行数据，表格读取数据
     */
    public List<String[]> getDatas() {
        return getDatas(true);
    }

    /**
     * @desc 获取表格读取数据,获取数据前，需要先转换数据
     */
    public List<String[]> getDatas(boolean dropFirstRow) {
        if (dropFirstRow && datas.size() > 0) {
            datas.remove(0);// 删除表头
        }

        return datas;
    }

    /**
     * @desc 获取读取表格的转换器
     */
    protected XMLReader getSheetParser(StylesTable styles, ReadOnlySharedStringsTable strings) throws SAXException {
        XMLReader parser = XMLReaderFactory.createXMLReader();
        parser.setContentHandler(new XSSFSheetXMLHandler(styles, strings, getContentHandler(), false));

        return parser;
    }

    public ISheetContentHandler getContentHandler() {
        return contentHandler;
    }

    public void setContentHandler(ISheetContentHandler contentHandler) {
        this.contentHandler = contentHandler;
    }

    /**
     * @desc 表格转换错误
     */
    public class ParseException extends Exception {
        private static final long serialVersionUID = -2451526411018517607L;

        public ParseException(Throwable t) {
            super("表格转换错误", t);
        }
    }

    public interface ISheetContentHandler extends SheetContentsHandler {
        /**
         * @desc 设置转换后的数据集，用于存放转换结果
         */
        void init(List<String[]> datas);
    }

    /**
     * @desc 默认表格解析handder
     */
    class DefaultSheetHandler implements ISheetContentHandler {
        /**
         * @desc 读取数据
         */
        private List<String[]> datas;
        private int columsLength;
        private String[] readRow;
        private ArrayList<String> fristRow = new ArrayList<String>();

        @Override
        public void init(List<String[]> datas) {
            this.datas = datas;
            //this.columsLength = columsLength;
        }

        @Override
        public void startRow(int rowNum) {
            if (rowNum != 0) {
                readRow = new String[columsLength];
            }
        }

        @Override
        public void endRow(int rowNum) {
            //将Excel第一行表头的列数当做数组的长度，要保证后续的行的列数不能超过这个长度，这是个约定。
            if (rowNum == 0) {
                columsLength = fristRow.size();
                readRow = fristRow.toArray(new String[fristRow.size()]);
            }else {
                readRow = fristRow.toArray(new String[columsLength]);
            }
            datas.add(readRow.clone());
            readRow = null;
            fristRow.clear();
        }

        @Override
        public void cell(String cellReference, String formattedValue, XSSFComment comment) {
            int index = getCellIndex(cellReference);//转换A1,B1,C1等表格位置为真实索引位置
            try {
                fristRow.set(index, formattedValue);
            } catch (IndexOutOfBoundsException e) {
                int size = fristRow.size();
                for (int i = index - size+1;i>0;i--){
                    fristRow.add(null);
                }
                fristRow.set(index,formattedValue);
            }
        }

        @Override
        public void headerFooter(String text, boolean isHeader, String tagName) {
        }

        /**
         * @desc 转换表格引用为列编号
         */
        public int getCellIndex(String cellReference) {
            String ref = cellReference.replaceAll("\\d+", "");
            int num = 0;
            int result = 0;
            for (int i = 0; i < ref.length(); i++) {
                char ch = cellReference.charAt(ref.length() - i - 1);
                num = (int) (ch - 'A' + 1);
                num *= Math.pow(26, i);
                result += num;
            }
            return result - 1;
        }
    }
}
