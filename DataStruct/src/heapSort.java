import java.util.*;

public class heapSort {
    public static void down(int[] lst,int i,int n){
        int tmp ;
        //i当前节点的子节点
        int child;

        for (tmp=lst[i];
             //i作为当前处理的二叉树节点，其必须有子节点，不能是叶子节点
             //所以i的子节点（2*i和2*i+1）小于n
             n>2*i+1;
             i=child){
            child = i*2+1;
            //对比i位置的子节点，判断是否为单子节点，左右子节点中挑选较小的
            if(n-1!=child && lst[child]>lst[child+1]) child++;
            //i位置的节点对比左右子节点中的较小的，将较小的值提升到i位置
            if(tmp>lst[child]){
                lst[i]=lst[child];
            }
            //若
            else break;
        }
        lst[i]=tmp;
    }
    public static void heapsort(int[] lst){
        for(int i=0;i<lst.length/2-1;i++)  down(lst,i,lst.length);
        for(int i=lst.length-1;i>0;i--){
            swapReference(lst,0, i);
            down(lst,0,i);
        }
    }
    private static  void swapReference(int[] h,int i,int n) {
        int x = h[i];
        h[i] = h[n];
        h[n] = x;
    }
    public static void main(String[] args) {
        int num = 10;
        int[] a = new int[num];
        Random rnd = new Random();
        for (int i = 0; i < num; i++) {
            int tmp = rnd.nextInt(37);
            System.out.print(tmp + " ");
            a[i] = tmp;
        }
        System.out.println("");
        heapsort(a);
        for (int i = 0; i < num; i++) {
            System.out.print(a[i] + " ");
        }

    }

}
