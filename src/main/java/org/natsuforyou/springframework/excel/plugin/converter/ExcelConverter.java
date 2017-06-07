package org.natsuforyou.springframework.excel.plugin.converter;

import org.apache.poi.ss.usermodel.Workbook;
import org.natsuforyou.springframework.excel.plugin.annotation.ExcelRequestBody;
import org.natsuforyou.springframework.excel.plugin.annotation.ExcelResponseBody;
import org.natsuforyou.springframework.excel.plugin.enums.ExcelType;

import java.io.InputStream;
import java.util.List;

public interface ExcelConverter {

    boolean supportsExcelType(ExcelType excelType);

    List<?> fromExcel(ExcelRequestBody excelRequestBody, InputStream input) throws Exception;

    <T> Workbook toExcel(ExcelResponseBody excelResponseBody, List<T> excelVoList) throws Exception;

}