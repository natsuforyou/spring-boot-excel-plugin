package org.natsuforyou.springframework.excel.plugin.config;

import org.natsuforyou.springframework.excel.plugin.converter.ExcelConverter;
import org.natsuforyou.springframework.excel.plugin.converter.ExcelConverterComposite;
import org.natsuforyou.springframework.excel.plugin.converter.HSSFExcelConverter;
import org.natsuforyou.springframework.excel.plugin.converter.XSSFExcelConverter;
import org.natsuforyou.springframework.excel.plugin.support.ExcelRequestResponseBodyHandler;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.util.ClassUtils;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

@ConditionalOnProperty(
        prefix = "spring.web.excel",
        value = {"enabled"},
        havingValue = "true",
        matchIfMissing = true
)
public class ExcelConfigurationSupport extends WebMvcConfigurerAdapter implements InitializingBean {

    private static boolean HSSFPresent =
            ClassUtils.isPresent("org.apache.poi.hssf.usermodel.HSSFWorkbook", ExcelConfigurationSupport.class.getClassLoader());

    private static boolean XSSFPresent =
            ClassUtils.isPresent("org.apache.poi.xssf.usermodel.XSSFWorkbook", ExcelConfigurationSupport.class.getClassLoader());

    private ExcelConverter excelConverters;


    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.excelConverters == null) {
            List<ExcelConverter> converters = getDefaultExcelConverters();
            this.excelConverters = new ExcelConverterComposite().addExcelConverters(converters);
        }
    }

    private List<ExcelConverter> getDefaultExcelConverters() {
        List<ExcelConverter> converters = new ArrayList<>(4);
        if (HSSFPresent) {
            converters.add(new HSSFExcelConverter());
        }
        if (XSSFPresent) {
            converters.add(new XSSFExcelConverter());
        }
        return converters;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        ExcelRequestResponseBodyHandler defaultExcelHandler = new ExcelRequestResponseBodyHandler();
        defaultExcelHandler.setConverters(excelConverters);
        argumentResolvers.add(defaultExcelHandler);
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        ExcelRequestResponseBodyHandler defaultExcelHandler = new ExcelRequestResponseBodyHandler();
        defaultExcelHandler.setConverters(excelConverters);
        returnValueHandlers.add(defaultExcelHandler);
    }
}
