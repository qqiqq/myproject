import java.util.*;

public class radixSort {
    public static void radix(int[] array,int d) {
        int n = 1;
        int len = array.length;
        int[][] buckets = new int[10][len];
        int[] cnt = new int[10];
        int k = 0;

        while(n<d){
            for(int num : array){
                int digit = (num/n)%10;
                buckets[digit][cnt[digit]] = num;
                cnt[digit]++;
            }
            for(int i=0;i<10;i++){
                if(cnt[i]!=0){
                    for(int j=0;j<cnt[i];j++){
                        array[k++] = buckets[i][j];
                    }
                }
                cnt[i] = 0;
            }
            k = 0;
            n*=10;
        }




/**        int n = 1;//代表位数对应的数：1,10,100...
        int k = 0;//保存每一位排序后的结果用于下一位的排序输入
        int length = array.length;
        int[][] bucket = new int[10][length];
        //排序桶用于保存每次排序后的结果，这一位上排序结果相同的数字放在同一个桶里
        int[] order = new int[10];//用于保存每个桶里有多少个数字
        while (n < d) {
            for (int num : array) //将数组array里的每个数字放在相应的桶里
            {
                int digit = (num / n) % 10;
                bucket[digit][order[digit]] = num;
                order[digit]++;
            }
            for (int i = 0; i < 10; i++)
                //将前一个循环生成的桶里的数据覆盖到原数组中用于保存这一位的排序结果
            {
                if (order[i] != 0)//这个桶里有数据，从上到下遍历这个桶并将数据保存到原数组中
                {
                    for (int j = 0; j < order[i]; j++) {
                        array[k] = bucket[i][j];
                        k++;
                    }
                }
                order[i] = 0;//将桶里计数器置0，用于下一次位排序
            }
            n *= 10;
            k = 0;//将k置0，用于下一轮保存位排序结果
        }
 **/

    }
    public static void main(String ...args){
        System.out.println((127/100)%10);
        int num=10;
        int[] a = new int[num];
        Random rnd = new Random();
        for(int i=0;i<num;i++){
            int tmp = rnd.nextInt(37);
            System.out.print(tmp + " ");
            a[i]=tmp;
        }
        System.out.println("");
        System.out.println("");
        radix(a,100);
        for(int i=0;i<num;i++){
            System.out.print(a[i] + " ");
        }

    }
}

