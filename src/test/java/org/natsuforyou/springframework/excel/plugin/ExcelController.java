package org.natsuforyou.springframework.excel.plugin;

import org.natsuforyou.springframework.excel.plugin.annotation.ExcelRequestBody;
import org.natsuforyou.springframework.excel.plugin.annotation.ExcelResponseBody;
import org.natsuforyou.springframework.excel.plugin.enums.ExcelType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ExcelController {

    @RequestMapping("/testImport")
    @ResponseBody
    public void testImport(@ExcelRequestBody(requireClass = ExcelVo.class, type = ExcelType.XLSX) List<ExcelVo> excelVoList) {
        System.out.println("111");
    }

    @RequestMapping("/testExport")
    @ExcelResponseBody(name = "test", type = ExcelType.XLSX)
    public List<ExcelVo> testExport() {
        List<ExcelVo> excelVoList = new ArrayList<ExcelVo>();
        ExcelVo excelVo = new ExcelVo();
        excelVo.setName("Rick");
        excelVo.setAge(11);
        excelVoList.add(excelVo);

        ExcelVo excelVo1 = new ExcelVo();
        excelVo1.setName("Tom");
        excelVo1.setAge(12);
        excelVoList.add(excelVo1);

        return excelVoList;
    }
}
