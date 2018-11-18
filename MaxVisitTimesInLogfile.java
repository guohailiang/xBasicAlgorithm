/*
 *****************************************************************************
 * Time    : 2018/10/29
 * Author  : Michael Guo
 * Project : xBasicAlgorithm
 * Class   : MaxVisitTimesInLogfile
 * Contact : XXXXXX@gmail.com
 * Software: Idea
 * Version : V1.0
 * Desc    : 从服务器的系统日记里面(千万的记录量)，找出一天中访问其次数
 *           最多的IP地址。
 * Logs    :
 *****************************************************************************
 */
import java.io.*;
import java.util.*;

public class MaxVisitTimesInLogfile {
    /**
     * @param fileName 文件路径
     */
    public void ipGenerate(String fileName){
        try {
            PrintWriter printWriter = new PrintWriter(fileName);//创建读取文件对象
            Random random = new Random();

            for (int ipIndex=0; ipIndex<1000; ipIndex++){
                String ipStr = "192.";
                //第一段固定为192，后面三段分别为[0-100]内的随机数
                ipStr += random.nextInt(100) + "." + random.nextInt(100) + "." +random.nextInt(100);
                printWriter.println(ipStr);
            }
            //关闭打开的文件读取流，释放资源
            printWriter.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将日记文件，分而治之成10个小文件
     * @param fileName 文件路径名
     */
    public void partitionLogsFile(String fileName){
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            PrintWriter outputPrintWriterArray[] = new PrintWriter[10];//10个输出流控制对象
            String ipStr = null;

            //10个子文件命名
            for(int partitionLogsFileIndex = 0; partitionLogsFileIndex < 10; partitionLogsFileIndex++){
                outputPrintWriterArray[partitionLogsFileIndex] = new PrintWriter(fileName +
                        partitionLogsFileIndex);
            }

            //hashCode对整个大文件散列处理
            while ((ipStr = bufferedReader.readLine()) != null){
                int hashIp = ipStr.hashCode()%10;
                hashIp = (hashIp >= 0 ? hashIp : hashIp+10);
                outputPrintWriterArray[hashIp].println(ipStr);
            }

            /*关闭分文件*/
            for (int partitionLogsFileIndex = 0; partitionLogsFileIndex < 10; partitionLogsFileIndex++){
                outputPrintWriterArray[partitionLogsFileIndex].close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 先分别读取10个分文件中访问次数最多的IP，再在这10个中挑选出整个日记文件中访问次数最多的
     * @param fileName 分文件名
     * @return 访问次数最多的Map.Entry
     */
    public Map.Entry<String, Integer> getMaxVisitTimesIp(String fileName){
        Map.Entry<String, Integer> maxEntry = null;

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            Map<String, Integer> ipMap = new HashMap<String, Integer>();
            String ipStr = null;

            //读取分文件内容并采用HashMap计数次数
            while ((ipStr = bufferedReader.readLine()) != null){
                if(ipMap.containsKey(ipStr)){
                    ipMap.put(ipStr, ipMap.get(ipStr)+1);
                }else{
                    ipMap.put(ipStr, 1);
                }
            }

            //取出ipMap中最大的值，即各分文件最大
            for (Map.Entry<String, Integer> ipMaps : ipMap.entrySet()){
                if(maxEntry == null || ipMaps.getValue() > maxEntry.getValue()){
                    maxEntry = ipMaps;
                }
            }

            bufferedReader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
            return maxEntry;
    }


    public static void main(String[] args) {

        String fileName = "C:\\Users\\59282\\Documents\\github_file\\xBasicAlgorithm\\resources\\ipLogs.txt";

        MaxVisitTimesInLogfile maxVisitTimesInLogfile = new MaxVisitTimesInLogfile();
        maxVisitTimesInLogfile.ipGenerate(fileName); //生成1000个随机的IP地址，输出到日记文件
        maxVisitTimesInLogfile.partitionLogsFile(fileName); //将大文件散列到10个分文件

        //取出每个分文件的最大
        List<Map.Entry<String, Integer>> entryList = new ArrayList<Map.Entry<String, Integer>>();
        for (int i = 0; i < 10; i++){
            entryList.add(maxVisitTimesInLogfile.getMaxVisitTimesIp(fileName + i));
        }

        //取出10个最大值（来自10个分文件）中的最大值
        Map.Entry<String, Integer> maxEntry = entryList.get(0);
        for (int i = 1; i < 10; i++){
            if (entryList.get(i).getValue() > maxEntry.getValue()){
                maxEntry = entryList.get(i);
            }
        }

        System.out.println(maxEntry.getKey() + ":" + maxEntry.getValue());

    }
}
