package org.natsuforyou.springframework.excel.plugin.enums;

public enum ExcelType {

    XLS("excel2003", ".xls"),
    XLSX("excel2007", ".xlsx");

    private String desc;

    private String suffixName;

    ExcelType(String desc, String suffixName) {
        this.desc = desc;
        this.suffixName = suffixName;
    }

    public String suffixName() {
        return suffixName;
    }
}