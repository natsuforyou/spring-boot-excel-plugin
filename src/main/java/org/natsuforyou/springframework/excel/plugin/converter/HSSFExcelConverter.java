package org.natsuforyou.springframework.excel.plugin.converter;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.natsuforyou.springframework.excel.plugin.enums.ExcelType;

import java.io.InputStream;

public class HSSFExcelConverter extends AbstractGenericPoiExcelConverter {

    @Override
    public boolean supportsExcelType(ExcelType excelType) {
        return ExcelType.XLS == excelType;
    }

    @Override
    protected Workbook createWorkBook(InputStream inputStream) throws Exception{
        return new HSSFWorkbook(inputStream);
    }

    @Override
    protected Workbook createWorkBook() throws Exception {
        return new HSSFWorkbook();
    }
}