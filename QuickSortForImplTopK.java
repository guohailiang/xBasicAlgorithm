/*
**********************************************************
 * Time    : 2018/10/28
 * Author  : Michael Guo
 * Project : xBasicAlgorithm
 * Class   : QuickSortForImplTopK
 * Contact : XXXXXX@gmail.com
 * Software: Idea
 * Version : V1.0
 * Desc    : 采用快排实现TopK问题
 * Logs    : []
***********************************************************
*/
public class QuickSortForImplTopK {

    /**
     * @param data 目标待处理数组
     * @param low  起始下标
     * @param high 结束下标
     * @return    中间位置下标，左边的比其大，右边的比其小
     */
    public int getMiddle(int[] data, int low, int high){
        int tmp = data[low];
        while (low < high)//直到low等于high结束循环
        {
            while (low < high && data[high] <= tmp){//如果从尾部位置开始的值比tmp小，符合预期，下标减1
                high--;
            }
            data[low] = data[high]; //将从尾部开始然后比tmp大的值，调换到下标为low的位置

            while (low < high && data[low] >= tmp){//如果从起始位置开始的值比tmp大，符合预期，下标加1
                low++;
            }
            data[high] = data[low]; //将从起始位置开始然后比tmp小的值，调换到下标为high的位置
        }
        data[low] = tmp; //最终low=high，上述循环结束，然后将data的第一个数值移动到middle（low，high）位置
        return low;
    }

   public void quickSort(int[] data, int low, int high, int k){
        if(low < high)
        {
            int middle = getMiddle(data, low, high);

            /*
              TopK问题的关键，如果middle位置起向左已经有了k个数，这k个数都比其大，而且
              middle右边的数都比其小，因此即找出了TopK个数
            */
            if(k-1 == middle){
                return;
            }

            quickSort(data, low, middle-1, k);
            quickSort(data,middle+1, high, k);
        }
    }

    //算法验证
    public static void main(String[] args) {
        int[] testData = {10,2,4,1,5,18,32,17};

        QuickSortForImplTopK qs = new QuickSortForImplTopK();
        qs.quickSort(testData, 0, testData.length-1,3);

        for(int i=0; i<testData.length; i++)
        {
           System.out.println(testData[i]);
        }

    }

}
