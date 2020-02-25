

import java.util.Random;

/**
 * Created by cookfront on 2017/3/18.
 */
public  class sort {
    public static void insertionSort(int[] arr) {
        int len = arr.length;

        int i;
        int j;

        for (i = 1; i < len; i++) {
            int temp = arr[i];
            for (j = i; j > 0 && temp < arr[j - 1]; j--) {
                arr[j] = arr[j - 1];
            }
            arr[j] = temp;
        }
    }

    public static void shellSort(int[] arr) {
        int len = arr.length;

        int i;
        int j;

        // 增量
        for (int gap = len / 2; gap > 0; gap /= 2) {
            for (i = gap; i < len; i++) {
                int temp = arr[i];
                for (j = i; j >= gap && temp < arr[j - gap]; j -= gap) {
                    arr[j] = arr[j - gap];
                }
                arr[j] = temp;
            }
        }
    }

    public static void mergeSort(int[] arr) {
        int[] tmpArray = new int[arr.length];
        mergeSort(arr, tmpArray, 0, arr.length - 1);
    }

    private static void mergeSort(int[] arr, int[] tmpArray, int left, int right) {
        if (left < right) {
            int center = (left + right) / 2;
            mergeSort(arr, tmpArray, left, center);
            mergeSort(arr, tmpArray, center + 1, right);
            merge(arr, tmpArray, left, center + 1, right);
        }
    }

    private static void merge(int[] arr, int[] tmpArray, int leftPos, int rightPos, int rightEnd) {
        int leftEnd = rightPos - 1;
        int tmpPos = leftPos;
        int numElements = rightEnd - leftPos + 1;

        while (leftPos <= leftEnd && rightPos <= rightEnd) {
            if (arr[leftPos] <= arr[rightPos]) {
                tmpArray[tmpPos++] = arr[leftPos++];
            } else {
                tmpArray[tmpPos++] = arr[rightPos++];
            }
        }

        while (leftPos <= leftEnd) {
            tmpArray[tmpPos++] = arr[leftPos++];
        }

        while (rightPos <= rightEnd) {
            tmpArray[tmpPos++] = arr[rightPos++];
        }

        // copy tmpArray back
        for (int i = 0; i < numElements; i++, rightEnd--) {
            arr[rightEnd] = tmpArray[rightEnd];
        }
    }

    public static void quickSort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(int[] a, int left, int right) {
        if (left == right) {
            return;
        }
        if (left + CUTOFF <= right) {
            int pivot = median3(a, left, right);

            // Begin partitioning
            int i = left, j = right - 1;
            for (; ; ) {
                while (a[++i] < pivot) {
                }
                while (a[--j] > pivot) {
                }
                if (i < j)
                    swapReferences(a, i, j);
                else
                    break;
            }

            swapReferences(a, i, right - 1);   // Restore pivot

            quickSort(a, left, i - 1);    // Sort small elements
            quickSort(a, i + 1, right);
        } else {
            insertionSort(a);
        }
    }

    private static void insertionSort(int[] a, int left, int right) {
        for (int p = left + 1; p <= right; p++) {
            int tmp = a[p];
            int j;

            for (j = p; j > left && tmp < a[j - 1]; j--)
                a[j] = a[j - 1];
            a[j] = tmp;
        }
    }

    private static int median3(int[] a, int left, int right) {
        int center = (left + right) / 2;
        if (a[center] < a[left])
            swapReferences(a, left, center);
        if (a[right] < a[left])
            swapReferences(a, left, right);
        if (a[right] < a[center])
            swapReferences(a, center, right);

        // Place pivot at position right - 1
        swapReferences(a, center, right - 1);
        return a[right - 1];
    }

    public static void swapReferences(int[] a, int index1, int index2) {
        int tmp = a[index1];
        a[index1] = a[index2];
        a[index2] = tmp;
    }

    private static final int NUM_ITEMS = 1000;
    private static int theSeed = 1;
    private static final int CUTOFF = 1;

    private static void checkSort(Integer[] a) {
        for (int i = 0; i < a.length; i++)
            if (a[i] != i)
                System.out.println("Error at " + i);
        System.out.println("Finished checksort");
    }

    public static void main(String... args) {
        int num = 10;
        int[] a = new int[num];
        Random rnd = new Random();
        for (int i = 0; i < num; i++) {
            int tmp = rnd.nextInt(37);
            System.out.print(tmp + " ");
            a[i] = tmp;
        }
        System.out.println("");
        quickSort(a);
        for (int i = 0; i < num; i++) {
            System.out.print(a[i] + " ");
        }
    }
}
/**        int num=10;
        int[] a = new int[num];
        Random rnd = new Random();
        for(int i=0;i<num;i++){
            int tmp = rnd.nextInt(37);
            System.out.print(tmp + " ");
            a[i]=tmp;
        }
        System.out.println("");
        quickSort(a);
        for(int i=0;i<num;i++){
            System.out.print(a[i] + " ");
        }

        
    }
**/


