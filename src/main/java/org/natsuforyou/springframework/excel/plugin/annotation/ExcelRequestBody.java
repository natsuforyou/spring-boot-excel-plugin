package org.natsuforyou.springframework.excel.plugin.annotation;

import org.natsuforyou.springframework.excel.plugin.enums.ExcelType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelRequestBody {

    Class<?> requireClass();

    String name() default "file";

    boolean hasSeq() default true;

    ExcelType type() default ExcelType.XLS;
}
