package readExcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.Map;

//创建读取excel监听器
public class ExcelListener extends AnalysisEventListener<ReadData> {
    //创建list集合封装最终的数据
    ArrayList<ReadData> ReadDatalist = new ArrayList<ReadData>();
    @Override
    //一行一行去读取excle内容
    public void invoke(ReadData readData, AnalysisContext analysisContext) {
        System.out.println(readData + "**************");
        ReadDatalist.add(readData);
    }
    //读取excel表头信息
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头信息："+headMap);
    }


    @Override
    //读取完成后执行
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
