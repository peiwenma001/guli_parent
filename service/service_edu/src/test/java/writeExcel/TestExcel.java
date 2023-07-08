package writeExcel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestExcel {
    public static void main(String[] args) {
//        实现写操作
//        1设置写入文件夹地址和excel文件名称
        String filename = "D:\\Book\\peiwen.xlsx";
        //2调用EasyExcel里面的方法实现写操作
        EasyExcel.write(filename, DemoData.class).sheet("test").doWrite(getData());
    }
//    创建一个方法返回list集合传入doWrite中
    private static List<DemoData> getData(){
        ArrayList<DemoData> list = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            DemoData demoData = new DemoData();
            demoData.setSno(i);
            demoData.setSname("tom" + i);
            list.add(demoData);
        }
        return list;
    }
}
