package org.natsuforyou.springframework.excel.plugin;

import org.natsuforyou.springframework.excel.plugin.annotation.ExcelAttribute;

public class ExcelVo {

    @ExcelAttribute(name = "name", column = "C")
    private String name;

    @ExcelAttribute(name = "age", column = "D")
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
