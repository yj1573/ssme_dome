package com.yj.utils.poi;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * excel导表类
 */
public class ExportExcelBean {
    private static final ExcelFunction DEFAULT = (f, v) -> {
        return v;
    };
    private ExcelFunction ef = DEFAULT;
    private Workbook wb;

    private ExportExcelBean() {
    }

    //创建对象
    public static ExportExcelBean createExportExcelBean() {
        return new ExportExcelBean();
    }

    //设置单元格值时回调函数
    public ExportExcelBean setExcelFunction(ExcelFunction ef) {
        this.ef = ef;
        return this;
    }

    //xls格式
    public ExportExcelBean xls() {
        this.wb = new HSSFWorkbook();
        return this;
    }

    //xlsx格式
    public ExportExcelBean xlsx() {
        this.wb = new XSSFWorkbook();
        return this;
    }

    //创建表格
    public Workbook createExcel(int pageSize, List<?> list, String strHeaders, String strFields) {
        if (list == null || list.size() == 0) {
            wb.createSheet("第0页");
            setStyle();
        } else {
            String[] headers = strHeaders.split(",");
            String[] fields = strFields.split(",");
            int total = list.size() / pageSize + (list.size() % pageSize > 0 ? 1 : 0);
            for (int page = 1; page <= total; page++) {
                Sheet sheet = wb.createSheet("第" + page + "页");
                setStyle();
                //创建标题 第一行
                int intRow = 0;
                //声明一个新行
                Row row = sheet.createRow(intRow);
                for (int i = 0; i < headers.length; i++) {
                    //创建一个单元格
                    row.createCell(i).setCellValue(headers[i]);
                }
                for (int i = pageSize * (page - 1); i < list.size() && i < pageSize * page; i++) {
                    Object t = list.get(i);
                    row = sheet.createRow(++intRow);
                    for (int j = 0; j < fields.length; j++) {
                        String f = fields[j];
                        String val = getVal(f, t);
                        val = ef.function(f, val);
                        row.createCell(j).setCellValue(val);
                    }
                }
            }
        }
        return wb;
    }


    /**
     * 输出到流
     *
     * @param out
     * @param pageSize
     * @param list
     * @param strHeaders
     * @param strFields
     */
    public void outExcel2Stream(OutputStream out, int pageSize, List<?> list, String strHeaders, String strFields) {
        try {
            createExcel(pageSize, list, strHeaders, strFields).write(out);
        } catch (Exception e) {
            new RuntimeException(e.getMessage());
        }
    }


    /**
     * 设置样式
     */
    private void setStyle() {
        //创建2个单元格样式
        CellStyle style = wb.createCellStyle();
        CellStyle style2 = wb.createCellStyle();
        DataFormat df = wb.createDataFormat();
        //创建2个单元格字体
        Font font1 = wb.createFont();
        Font font2 = wb.createFont();
        //设置字体类型1到12号，蓝色和粗体
        font1.setFontHeightInPoints((short) 12);
        font1.setColor((short) 10);
        font1.setBold(true);
        //设置字体类型2到10号，红色和粗体
        font2.setFontHeightInPoints((short) 10);
        font2.setColor((short) 10);
        font2.setBold(true);
        //设置单元格样式和格式
        style.setFont(font1);
        style.setDataFormat(df.getFormat("#,##0.0"));
        //设置其他单元格样式和格式
        style2.setBorderBottom(BorderStyle.THIN);
        style2.setDataFormat((short) 49);
        style2.setFont(font2);
    }

    private String getVal(String field, Object t) {
        Object o = "";
        if (t instanceof Map) {
            o = ((Map) t).get(field);
        } else {
            try {
                Field f = t.getClass().getDeclaredField(field);
                f.setAccessible(true);
                o = f.get(t);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        if (o == null) {
            o = "";
        }
        return o.toString();
    }

}
