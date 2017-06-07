package org.natsuforyou.springframework.excel.plugin.converter;

import org.apache.poi.ss.usermodel.Workbook;
import org.natsuforyou.springframework.excel.plugin.annotation.ExcelRequestBody;
import org.natsuforyou.springframework.excel.plugin.annotation.ExcelResponseBody;
import org.natsuforyou.springframework.excel.plugin.enums.ExcelType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ExcelConverterComposite implements ExcelConverter {

    private Logger logger = LoggerFactory.getLogger(ExcelConverterComposite.class);

    private final List<ExcelConverter> excelConverters = new LinkedList<>();

    private final Map<ExcelType, ExcelConverter> excelConvertersCache = new ConcurrentHashMap<>(4);

    public ExcelConverterComposite addExcelConverters(List<ExcelConverter> converters) {
        if (converters != null) {
            for (ExcelConverter converter : converters) {
                this.excelConverters.add(converter);
            }
        }
        return this;
    }

    @Override
    public boolean supportsExcelType(ExcelType excelType) {
        return (getExcelConverter(excelType) != null);
    }

    @Override
    public List<?> fromExcel(ExcelRequestBody excelRequestBody, InputStream input) throws Exception {
        ExcelType excelType = excelRequestBody.type();
        ExcelConverter converter = getExcelConverter(excelType);
        if (converter == null) {
            throw new IllegalArgumentException("Unknown converter excelType [" + excelType.name() + "]");
        }
        return converter.fromExcel(excelRequestBody, input);
    }


    @Override
    public <T> Workbook toExcel(ExcelResponseBody excelResponseBody, List<T> excelVoList) throws Exception{
        ExcelType excelType = excelResponseBody.type();
        ExcelConverter converter = getExcelConverter(excelType);
        if (converter == null) {
            throw new IllegalArgumentException("Unknown converter type [" + excelType.name() + "]");
        }
        return converter.toExcel(excelResponseBody, excelVoList);
    }

    private ExcelConverter getExcelConverter(ExcelType excelType) {
        ExcelConverter result = this.excelConvertersCache.get(excelType);
        if (result == null) {
            for (ExcelConverter excelConverter : this.excelConverters) {
                if (logger.isTraceEnabled()) {
                    logger.trace("Testing if converter converter [" + excelConverter + "] supports [" + excelType.name() + "]");
                }
                if (excelConverter.supportsExcelType(excelType)) {
                    result = excelConverter;
                    this.excelConvertersCache.put(excelType, result);
                    break;
                }
            }
        }
        return result;
    }
}