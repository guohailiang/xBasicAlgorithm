/*
 **********************************************************
 * Time    : 2018/11/02
 * Author  : Michael Guo
 * Project : xBasicAlgorithm
 * Class   : HeapSortForImplTopK
 * Contact : XXXXXX@gmail.com
 * Software: Idea
 * Version : V1.0
 * Desc    : 采用堆排序实现TopK问题
 * Logs    : []
 ***********************************************************
 */
 public class HeapSortForImplTopK {
    //交换函数
    private void swap(int[] data, int i, int j) {
        int tmp;
        tmp = data[i];
        data[i] = data[j];
        data[j] = tmp;
    }

    /**
     * 建立大顶堆，即二叉树每个父节点的值都比左右子节点值大
     * @param data 输入数组
     * @param lastIndex 最后一个元素下标
     */

    private void buildMaxHeap(int[] data, int lastIndex){
        //从lastIndex节点的父节点(lastIndex-1)/2开始
        for(int i = (lastIndex-1)/2; i >= 0; i--){
            //临时保存正在判断的节点
            int current = i;
            //current*2+1<=lastIndex表明当前节点子节点存在
            while (current*2+1 <=lastIndex ){
                //取其左节点作为最大值flag的初始位置
                int biggest = current*2+1;
                //current*2+1<lastIndex表明当前节点的右节点存在
                if (biggest < lastIndex){
                    //左子节点小于右子节点，则标志位→移
                    if(data[biggest] < data[biggest+1]){
                        biggest++;
                    }
                }
                //如果当前节点的值小于左右子节点中最大的，则交换之
                if (data[current] < data[biggest]){
                    swap(data, current, biggest);
                    //如果发生了交换，则current游标（父节点）需要移动到biggest（子节点）处，直到current位于叶子节点跳出while循环
                    //以保证发生交换变化后的子堆也是大顶堆
                    current = biggest;
                }else{
                    break;
                }
            }
        }
    }

    /**
     * 堆排序实现框架代码
     * @param data 输入数组
     * @param k 取出前K个最大的数
     */
    public void heapSort(int[] data, int k) {
        int length = data.length;
        //开始排序，交换k次之后，得到最大的k个数
        for(int i = 0; i < k; i++){
            //循环建大顶堆
            buildMaxHeap(data, length-1-i);
            swap(data, 0,length-1-i);
        }
    }

    //算法验证
    public static void main(String[] args) {
        int[] testData = {10, 29, 4, 19, 5, 18, 32};
        final int TOP_K = 3;
        HeapSortForImplTopK heapSortForImplTopK = new HeapSortForImplTopK();
        heapSortForImplTopK.heapSort(testData, TOP_K);

        for (int i = 0; i < TOP_K; i++) {
            System.out.println(testData[testData.length-1-i]);
        }

    }
}


