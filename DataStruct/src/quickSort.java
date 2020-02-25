
import java.util.Random;

public class quickSort {
    public static void quicksort(int[] a){
        sort(a,0,a.length-1);
    }
    public static void sort(int[] a, int left,int right){
        if(left==right) return;
//        if (left + 2 <= right) {
            int pivot = media3(a, left, right);
            int i = left;
            int j = right - 1;
            while (true) {
                while (a[++i] < pivot) {
                }
                ;
                while (a[--j] > pivot) {
                }
                ;
                if (i < j) swapRef(a, i, j);
                else break;
            }
            swapRef(a, i, right - 1);
            sort(a, left, i - 1);
            sort(a, i + 1, right);
 //       }
 /**       else {
            for (int p = left + 1; p <= right; p++) {
                int tmp = a[p];
                int j;

                for (j = p; j > left && tmp<a[j - 1]; j--)
                    a[j] = a[j - 1];
                a[j] = tmp;
            }

        }
  **/
    }
    private static int media3(int[] a, int left,int right){
        int center = (left + right) / 2;
        if (a[center] < a[left])
            swapRef(a, left, center);
        if (a[right] < a[left])
            swapRef(a, left, right);
        if (a[right] < a[center])
            swapRef(a, center, right);

        // Place pivot at position right - 1
        swapRef(a, center, right - 1);
        return a[right - 1];
/**  int media = (right+left)/2;
 if(a[media]<a[left]) swapRef(a,left,media);
 if(a[right]<a[media]) swapRef(a,media,right);
 if(a[right]<a[left]) swapRef(a,left,right);


 swapRef(a,media,right-1);
        return a[right-1];
 **/

    }
    private static void swapRef(int[] a,int n,int m ){
        int tmp = a[m];
        a[m] = a[n];
        a[n] = tmp;
    }
    public static void main(String ...ars){
        int num=20;
        int[] a = new int[num];
        Random rnd = new Random();
        for(int i=0;i<num;i++){
            int tmp = rnd.nextInt(37);
            System.out.print(tmp + " ");
            a[i]=tmp;
        }
        System.out.println("");
        quicksort(a);
        for(int i=0;i<num;i++){
            System.out.print(a[i] + " ");
        }
    }
}