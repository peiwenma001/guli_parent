package readExcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ReadData {
    //设置列对应的属性
    @ExcelProperty(index = 0)
    private String xibie;
    @ExcelProperty(index = 1)
    private String banji;
    @ExcelProperty(index = 2)
    private String shijian;
    @ExcelProperty(index = 3)
    private String kemu;
    @ExcelProperty(index = 4)
    private String didian;
    @ExcelProperty(index = 5)
    private String laoshi;
}
