package org.natsuforyou.springframework.excel.plugin.support;

import org.apache.poi.ss.usermodel.Workbook;
import org.natsuforyou.springframework.excel.plugin.annotation.ExcelRequestBody;
import org.natsuforyou.springframework.excel.plugin.annotation.ExcelResponseBody;
import org.natsuforyou.springframework.excel.plugin.enums.ExcelType;
import org.natsuforyou.springframework.excel.plugin.converter.ExcelConverter;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExcelRequestResponseBodyHandler implements HandlerMethodArgumentResolver, HandlerMethodReturnValueHandler {

    private ExcelConverter converters;

    public void setConverters(ExcelConverter converters) {
        this.converters = converters;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(ExcelRequestBody.class);
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return returnType.hasMethodAnnotation(ExcelResponseBody.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(servletRequest, MultipartHttpServletRequest.class);

        ExcelRequestBody annotation = parameter.getParameterAnnotation(ExcelRequestBody.class);
        if (multipartRequest != null) {
            List<Object> result = new ArrayList<>();
            List<MultipartFile> files = multipartRequest.getFiles(annotation.name());
            for (MultipartFile file : files) {
                if (converters.supportsExcelType(annotation.type())) {
                    List<?> part = converters.fromExcel(annotation, file.getInputStream());
                    result.addAll(part);
                }
            }
            return result;
        }
        return null;

    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        mavContainer.setRequestHandled(true);
        HttpServletResponse servletResponse = webRequest.getNativeResponse(HttpServletResponse.class);

        ExcelResponseBody annotation = returnType.getMethodAnnotation(ExcelResponseBody.class);
        ExcelType type = annotation.type();
        String fileName = annotation.name() + type.suffixName();

        servletResponse.setContentType("application/vnd.ms-converter");
        servletResponse.setHeader("content-disposition", "attachment;filename=" + fileName);
        List<?> excel;
        if (returnValue instanceof List) {
            excel = (List<?>) returnValue;
        } else {
            excel = Collections.singletonList(returnValue);
        }

        if (converters.supportsExcelType(type)) {
            try (Workbook workbook = converters.toExcel(annotation, excel)) {
                workbook.write(servletResponse.getOutputStream());
                servletResponse.flushBuffer();
            } catch (IOException ignored) {
                ;
            }
        }


    }

}
