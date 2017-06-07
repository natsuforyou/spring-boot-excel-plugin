# spring-boot-excel-plugin
a simple way to convert excel file, based on spring mvc

一个简单的方法来转换excel文件，基于spring mvc

## quick start
a typical spring boot application start class

一个典型的spring boot应用启动类
```java
@SpringBootApplication
public class Application {
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

a excel mapping value object class annotated with @ExcelAttribute

用@ExcelAttribute注释的excel vo
```java
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
```

a typical controller class

一个典型的controller类

When using parameter annotated with @ExcelRequestBody, the excel file will be converted to excel vo at the spring mvc binding parameters time

当使用@ExcelRequestBody注释的参数时，excel文件将在spring mvc绑定参数时被转换成excel vo

When using method annotated with @ExcelResponseBody, the controller return value will be converted to excel file at the spring mvc building return result

当使用@ExcelResponseBody注释的方法时，controller返回值将在spring mvc构建返回结果时被转换成excel文件
```java
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
```
enjoy it!

好好享受！