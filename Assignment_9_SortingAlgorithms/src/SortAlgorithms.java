// Timothy Kwock
// Assignment 9 - Table of Sorted Items

public class SortAlgorithms
{
   public static int[] createNewArray(int arrayLength)
   {
      int[] newArray = new int[arrayLength];
      for(int i = 0; i < arrayLength; i++)
      {
         newArray[i] = (int)(Math.random() * 1000);
      }
      return newArray;
   }
   
   public static void main(String[] args)
   {
      System.out.println("Array Size      50,000    100,000   150,000   "
         + "200,000   250,000   300,000\n");
      System.out.printf("%-16s", "Insertion Sort");
      for(int i = 50_000; i <= 300_000; i+= 50_000)
      {
         // Insertion Sort
         long startTime = System.nanoTime();
         InsertionSort.insertionSort(createNewArray(i));
         long endTime = System.nanoTime();
         long executionTime = endTime - startTime;
         System.out.printf("%1.1ens ", (float)executionTime);
      }
      System.out.println("\n");
      System.out.printf("%-16s", "Bubble Sort");
      for(int i = 50_000; i <= 300_000; i+= 50_000)
      {
         // Insertion Sort
         long startTime = System.nanoTime();
         BubbleSort.bubbleSort(createNewArray(i));
         long endTime = System.nanoTime();
         long executionTime = endTime - startTime;
         System.out.printf("%1.1ens ", (float)executionTime);
      }
      System.out.println("\n");
      System.out.printf("%-16s", "Merge Sort");
      for(int i = 50_000; i <= 300_000; i+= 50_000)
      {
         // Merge Sort
         long startTime = System.nanoTime();
         MergeSort.mergeSort(createNewArray(i));
         long endTime = System.nanoTime();
         long executionTime = endTime - startTime;
         System.out.printf("%1.1ens ", (float)executionTime);
      }
      System.out.println("\n");
      System.out.printf("%-16s", "Quick Sort");
      for(int i = 50_000; i <= 300_000; i+= 50_000)
      {
         // Quick Sort
         long startTime = System.nanoTime();
         QuickSort.quickSort(createNewArray(i));
         long endTime = System.nanoTime();
         long executionTime = endTime - startTime;
         System.out.printf("%1.1ens ", (float)executionTime);
      }
      System.out.println("\n");
      System.out.printf("%-16s", "Heap Sort");
      for(int i = 50_000; i <= 300_000; i+= 50_000)
      {
         // Heap Sort
         long startTime = System.nanoTime();
         HeapSort.heapSort(createNewArray(i));
         long endTime = System.nanoTime();
         long executionTime = endTime - startTime;
         System.out.printf("%1.1ens ", (float)executionTime);
      }
      System.out.println("\n");
      System.out.printf("%-16s", "Radix Sort");
      for(int i = 50_000; i <= 300_000; i+= 50_000)
      {
         // Heap Sort
         long startTime = System.nanoTime();
         RadixSort.radixSort(createNewArray(i));
         long endTime = System.nanoTime();
         long executionTime = endTime - startTime;
         System.out.printf("%1.1ens ", (float)executionTime);
      }
   }
}

class InsertionSort
{
   public static void insertionSort(int[] array)
   {
      for(int i = 1; i < array.length; i++)
      {
         int currElement = array[i];
         int k;
         for(k = i - 1; k >= 0 && array[k] > currElement; k--)
         {
            array[k + 1] = array[k];
         }
         array[k + 1] = currElement;
      }
   }
}

class BubbleSort
{
   public static void bubbleSort(int[] array)
   {
      boolean needNextPass = true;
      for(int k = 1; k < array.length && needNextPass; k++)
      {
         needNextPass = false;
         for(int i = 0; i < array.length - k; i++)
         {
            if(array[i] > array[i + 1])
            {
               // swap array[i] with array[i + 1]
               int temp = array[i];
               array[i] = array[i + 1];
               array[i + 1] = temp;
               
               needNextPass = true;
            }
         }
      }
   }
}

class MergeSort
{
   public static void mergeSort(int[] array)
   {
      if(array.length > 1)
      {
         // Merge sort first half
         int[] firstHalf = new int[array.length / 2];
         System.arraycopy(array, 0, firstHalf, 0, array.length / 2);
         mergeSort(firstHalf);
         
         // Merge sort second half
         int secondHalfLength = array.length - array.length / 2;
         int[] secondHalf = new int[secondHalfLength];
         System.arraycopy(array, array.length / 2, secondHalf, 0, 
               secondHalfLength);
         mergeSort(secondHalf);
         
         // Merge firstHalf with secondHalf into list
         merge(firstHalf, secondHalf, array);
      }
   }
   
   // Merge 2 sorted lists
   public static void merge(int[] array1, int[] array2, int[] temp)
   {
      int current1 = 0;
      int current2 = 0;
      int current3 = 0;
      
      while(current1 < array1.length && current2 < array2.length) 
      {
         if(array1[current1] < array2[current2])
            temp[current3++] = array1[current1++];
         else
            temp[current3++] = array2[current2++];
      }
      
      while(current1 < array1.length)
         temp[current3++] = array1[current1++];
      
      while(current2 < array2.length)
         temp[current3++] = array2[current2++];
   }
}

class QuickSort
{
   public static void quickSort(int[] array)
   {
      quickSort(array, 0, array.length - 1);
   }
   
   public static void quickSort(int[] array, int first, int last)
   {
      if(last > first)
      {
         int pivotIndex = partition(array, first, last);
         quickSort(array, first, pivotIndex - 1);
         quickSort(array, pivotIndex + 1, last);
      }
   }
   
   public static int partition(int[] array, int first, int last)
   {
      int pivot = array[first];
      int low = first + 1;
      int high = last;
      
      while(high > low)
      {
         // Search forward from left
         while(low <= high && array[low] <= pivot)
            low++;
         
         // Search backward from right
         while(low <= high && array[high] > pivot)
            high--;
         
         // Swap 2 elements in the list
         if(high > low)
         {
            int temp = array[high];
            array[high] = array[low];
            array[low] = temp;
         }
      }
      
      while(high > first && array[high] >= pivot)
         high--;
      
      // Swap pivot with list[high]
      if(pivot > array[high])
      {
         array[first] = array[high];
         array[high] = pivot;
         return high;
      }
      else
      {
         return first;
      }
   }
}

class Heap<E extends Comparable<E>>
{
   private java.util.ArrayList<Integer> list = new java.util.ArrayList<>();
   
   /** Create a default heap using a natural order for comparison */
   public Heap() 
   {
   }   
  
   /** Create a heap from an array of objects */
   public Heap(int[] objects) 
   {
      for (int i = 0; i < objects.length; i++)
         add(objects[i]);
   }

   /** Add a new object into the heap */
   public void add(int newObject) 
   {
      list.add(newObject); // Append to the heap
      int currentIndex = list.size() - 1; // The index of the last node

      while (currentIndex > 0) 
      {
         int parentIndex = (currentIndex - 1) / 2;
      // Swap if the current object is greater than its parent
      if (list.get(currentIndex).compareTo(list.get(parentIndex)) > 0) 
      {
         int temp = list.get(currentIndex);
         list.set(currentIndex, list.get(parentIndex));
         list.set(parentIndex, temp);
      }
      else
         break; // the tree is a heap now

      currentIndex = parentIndex;
      }
   }

   /** Remove the root from the heap */
   public int remove() {
      if (list.size() == 0) 
         return 0;
      
      int removedObject = list.get(0);
      list.set(0, list.get(list.size() - 1));
      list.remove(list.size() - 1);

      int currentIndex = 0;
      while (currentIndex < list.size()) {
         int leftChildIndex = 2 * currentIndex + 1;
         int rightChildIndex = 2 * currentIndex + 2;

         // Find the maximum between two children
         if (leftChildIndex >= list.size()) 
            break; // The tree is a heap
         int maxIndex = leftChildIndex;
         if (rightChildIndex < list.size()) 
         {
            if (list.get(maxIndex).compareTo(list.get(rightChildIndex)) < 0) 
            {
               maxIndex = rightChildIndex;
            }
         }

         // Swap if the current node is less than the maximum
         if (list.get(currentIndex).compareTo(list.get(maxIndex)) < 0) {
            int temp = list.get(maxIndex);
            list.set(maxIndex, list.get(currentIndex));
            list.set(currentIndex, temp);
            currentIndex = maxIndex;
         }
         else
            break; // The tree is a heap
      }

      return removedObject;
   }

   /** Get the number of nodes in the tree */
   public int getSize() 
   {
      return list.size();
   }
  
   /** Return true if heap is empty */
   public boolean isEmpty() 
   {
      return list.size() == 0;
   }
}

class HeapSort 
{
   /** Heap sort method */
   public static <E extends Comparable<E>> void heapSort(int[] list)
   {
     // Create a Heap of integers
     Heap<Integer> heap = new Heap<>();

     // Add elements to the heap
     for (int i = 0; i < list.length; i++)
       heap.add(list[i]);

     // Remove elements from the heap
     for (int i = list.length - 1; i >= 0; i--)
       list[i] = heap.remove();
     }
}

class RadixSort
{
   public static void radixSort(int[] list)
   {
      int max = max(list);

      for (int exp = 1; max / exp > 0; exp *= 10)
      {
         countSort(list, exp);
      }
   }

   public static void countSort(int[] list, int exp)
   {
      int[] tempList = new int[list.length];
      int[] count = new int[10];
      for (int i = 0; i < list.length; i++)
      {
         count[(list[i] / exp) % 10]++;
      }
      for (int i = 1; i < 10; i++)
      {
         count[i] = count[i - 1] + count[i];

      }
      for (int i = list.length - 1; i >= 0; i--)
      {
         tempList[count[(list[i] / exp) % 10] - 1] = list[i];
         count[(list[i] / exp) % 10]--;
      }
      for (int i = 0; i < list.length; i++)
      {
         list[i] = tempList[i];
      }

   }

   public static int getKey(int num, int exp)
   {
      return (num / exp) % 10;
   }

   public static int max(int[] list)
   {
      if (list.length == 0)
      {
         return 0;
      }
      int max = list[0];
      for (int i = 0; i < list.length; i++)
      {
         if (list[i] > max)
         {
            max = list[i];
         }
      }
      return max;
   }

   public static int[] generateList(int len)
   {
      int[] list = new int[len];
      for (int i = 0; i < list.length; i++)
      {
         list[i] = (int) (Math.random() * 301);
      }
      return list;
   } 
}


/* --------------------- Output -----------------------------------------

Array Size      50,000    100,000   150,000   200,000   250,000   300,000

Insertion Sort  8.9e+08ns 3.3e+09ns 1.7e+09ns 3.0e+09ns 4.7e+09ns 6.8e+09ns 

Bubble Sort     2.4e+09ns 1.0e+10ns 2.3e+10ns 4.1e+10ns 6.4e+10ns 9.3e+10ns 

Merge Sort      1.0e+07ns 2.1e+07ns 2.0e+07ns 2.6e+07ns 2.5e+07ns 3.5e+07ns 

Quick Sort      2.1e+07ns 1.7e+07ns 2.5e+07ns 4.0e+07ns 5.8e+07ns 7.8e+07ns 

Heap Sort       6.6e+07ns 3.6e+07ns 6.2e+07ns 6.5e+07ns 8.9e+07ns 1.1e+08ns 

Radix Sort      7.0e+06ns 4.7e+06ns 6.1e+06ns 7.5e+06ns 6.9e+06ns 9.2e+06ns 

------------------------------------------------------------------------- */