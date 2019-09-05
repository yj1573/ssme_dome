package com.yj.utils.poi;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * excel读取类
 */
public class ReadExcelBean {
    private int startSheet = 0;// 开始页
    private int endSheet = 1;// 结束页
    private int startRow = 0;// 开始行
    private int endRow = Integer.MAX_VALUE;// 结束行
    private int startColumn = 0;// 开始列
    private int endColumn = 1;// 结束列
    private List<List<String>> list = new ArrayList<>();

    private ReadExcelBean(int startSheet, int endSheet, int startRow, int startColumn, int endColumn) {
        super();
        this.startSheet = startSheet - 1;
        this.endSheet = endSheet;
        this.startRow = startRow - 1;
        this.startColumn = startColumn - 1;
        this.endColumn = endColumn;
    }

    //创建对象
    public static ReadExcelBean createReadExcelBean(int startSheet, int endSheet, int startRow, int startColumn, int endColumn) {
        return new ReadExcelBean(startSheet, endSheet, startRow, startColumn, endColumn);
    }


    /**
     * 获取结果
     *
     * @return
     */
    public List<List<String>> getExcelList() {
        return list;
    }

    /**
     * 读取xlsx格式
     *
     * @param file
     */
    public void readXlsx(File file) {
        try {
            readXlsx(new FileInputStream(file));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void readXlsx(InputStream in) {
        try {
            readExcel(new XSSFWorkbook(in));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 读取xls格式
     *
     * @param file
     */
    public void readXls(File file) {
        try {
            readXls(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void readXls(InputStream in) {
        try {
            readExcel(new HSSFWorkbook(in));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //读取excel内容
    private void readExcel(Workbook workbook) {
        // 读取一页,页数从0开始到workbook.getNumberOfSheets()
        for (int numSheet = startSheet; numSheet < endSheet; numSheet++) {
            Sheet sheet = workbook.getSheetAt(numSheet);
            if (sheet != null) {
                // 读取每一行,行数从0开始到sheet.getLastRowNum()
                for (int numRow = startRow; numRow <= sheet.getLastRowNum(); numRow++) {
                    Row row = sheet.getRow(numRow);
                    if (row != null) {
                        List<String> rowList = new ArrayList<>();
                        // 读取一行的每列，列从0开始
                        for (int col = startColumn; col < endColumn; col++) {
                            Cell cell = row.getCell(col);
                            if (cell != null) {
                                cell.setCellType(CellType.STRING);
                                rowList.add(this.getVal(cell));
                            }
                        }
                        list.add(rowList);
                    }
                }
            }
        }
    }

    //获取单元格内容
    private String getVal(Cell cell) {
        return cell.getStringCellValue().replace("\n", "").replace("\r", "").trim();
    }


}
