import java.util.Random;


public class countingRadixSort {

        public static int[] sort(int[] arr,int min,int max){
            int[] temp = new int[max-min+1];
            for(int i=0;i<arr.length;i++){
                System.out.print(arr[i] + " ");
                temp[arr[i]-min] += 1;
            }
            System.out.println();
            System.out.println("before increment the temp array is: ");
            for(int i=0;i<temp.length-1;i++){
                System.out.print(temp[i] + " ");
            }
            System.out.println();
            for(int j=0;j<temp.length-1;j++){
                temp[j+1]+=temp[j];
            }
            System.out.println("after increment the temp array is: ");
            for(int i=0;i<temp.length-1;i++){
                System.out.print(temp[i] + " ");
            }
            System.out.println();
            int[] out = new int[arr.length];
            for(int k=arr.length-1;k>=0;k--){
                int tmp = arr[k];
                out[temp[tmp-min]-1]=tmp;
                temp[tmp-min]-=1;
            }
            return out;
        }
        public static void main(String ...args){
            int num=10;
            int max = 10;
            int min = 0;
            int[] a = new int[num];
            Random rnd = new Random();
            for(int i=0;i<num;i++){
                int tmp = rnd.nextInt(max)%(max-min+1)+min;
                System.out.print(tmp + " ");
                a[i]=tmp;
            }
            System.out.println("");
            int[] out = sort(a,min,max);
            for(int i=0;i<num;i++){
                System.out.print(out[i] + " ");
            }
        }

}

