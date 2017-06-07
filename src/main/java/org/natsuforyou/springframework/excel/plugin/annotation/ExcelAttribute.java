package org.natsuforyou.springframework.excel.plugin.annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelAttribute {

    /**
     * @return Excel中的列名
     */
    String name();

    /**
     * @return 列名对应的A,B,C,D...
     */
    String column();

    /**
     * @return 是否导出数据
     */
    boolean isExport() default true;

    /**
     * @return 是否为重要字段（整列标红,着重显示）
     */
    boolean isMark() default false;

}