import java.util.Random;

public class mergeSort {
    public static void mergesort(int[] arr){
        int[] tmp = new int[arr.length];
        merg(arr,tmp,0,arr.length-1);
    }
    private static void merg(int[] arr,int[] tmp,int left,int right){
        if(left < right){
            int center = (left + right) / 2;
            System.out.println("left:" + left + ", center:" + center + ", right:" + right);
            merg(arr,tmp,left,center);
            merg(arr,tmp,center+1,right);
            sort(arr,tmp,left,center+1,right);
        }
    }
    private static void sort(int[] arr,int[] tmp,int left,int center,int right){
        int leftEnd = center - 1;
        int tmpPos = left;
        int elementsNum = right - left + 1;
        while(left <= leftEnd && center <= right){
            if (arr[left] <= arr[center]) {
                tmp[tmpPos++] = arr[left++];
 //               left++;
  //              tmpPos++;
            } else {
                tmp[tmpPos++] = arr[center++];
 //               center++;
 //               tmpPos++;
            }
        }
        while(left <= leftEnd) tmp[tmpPos++] = arr[left++];
        while(center <= right) tmp[tmpPos++] = arr[center++];
        for(int i=0;i<elementsNum;i++,right--) arr[right] = tmp[right];
    }
    public static void  main(String ...args){
        int num=10;
        int[] a = new int[num];
        Random rnd = new Random();
        for(int i=0;i<num;i++){
            int tmp = rnd.nextInt(37);
            System.out.print(tmp + " ");
            a[i]=tmp;
        }
        System.out.println("");
        mergesort(a);
        for(int i=0;i<num;i++){
            System.out.print(a[i] + " ");
        }
    }
}
