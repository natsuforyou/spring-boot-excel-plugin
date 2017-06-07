package org.natsuforyou.springframework.excel.plugin.converter;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.natsuforyou.springframework.excel.plugin.enums.ExcelType;

import java.io.InputStream;

public class XSSFExcelConverter extends AbstractGenericPoiExcelConverter {

    @Override
    public boolean supportsExcelType(ExcelType excelType) {
        return ExcelType.XLSX == excelType;
    }

    @Override
    protected Workbook createWorkBook(InputStream inputStream) throws Exception {
        return new XSSFWorkbook(inputStream);
    }

    @Override
    protected Workbook createWorkBook() throws Exception {
        return new XSSFWorkbook();
    }
}