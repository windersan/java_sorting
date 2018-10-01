/*
 * Implement three sorting algorithms
 */
package cs310sort;

import java.util.*;
import java.io.*;
/**
 *
 * @author Devin Andres Salemi
 * @version 1, Project 7
 */
public class CS310Sort {
    static final int SIZE = 50000;
    /**
     * The main function will make the calls to the various sorting functions
     */ 
   public static void main(String[] args) {
        
        
        
        double[][] results = new double[4][3];
        
        
        for(int i = 0; i < 3; i++){
            //Set aside some memory for the lists
            Integer[] list1 = new Integer[SIZE];
            Integer[] list2 = new Integer[SIZE];
            Integer[] list3 = new Integer[SIZE];
     
            //Generate the lists to be sorted
            Random random = new Random();
            for(int j = 0; j < SIZE; j++){
                list1[j] = list2[j] = list3[j] = random.nextInt(100000); 
            }
        
            System.out.println("Starting sort #" + (i+1));
        
            long t1 = selectionSort(list1);
            System.out.println("    Selection Sort time: " + t1 + " ns");
            results[i][0] = t1;
            long t2 = shellSort(list2);
            System.out.println("    Shell Sort time: " + t2 + " ns");
            results[i][1] = t2;
            long t3 = mergeSort(list3);
            System.out.println("    Merge Sort time: " + t3 + " ns");
            results[i][2] = t3;
        
        
            verify(list1,list2,list3);
                    
                    
                  
        }
        
        //normalize the results to convert to seconds from nanoseconds
        for(int m = 0; m < 3; m++){
            for(int n = 0; n < 3; n++){
            results[m][n] /= 1000000000;   
            }   
        }
        
        for(int p = 0;p<3;p++){
            results[3][p] =(results[0][p]+results[1][p]+results[2][p])/3;
        
        }
        
        
        
        createReport("output/sortResults.txt", results);
        
        
    }
    
    
    
    
    
    
    
    /**
     * Selection sort implementation
     * @param list
     * @return 
     */
    static long selectionSort(Integer[] list){
        long start = System.nanoTime();
        
        int n  = SIZE;
        for(int fill = 0; fill < (n-1); fill++){
            int min = fill;
            for(int next = fill + 1; next < n; next++){
                if(list[min] > list[next]){
                    min = next;
                }
            }
            
            Integer temp = list[fill];
            list[fill] = list[min];
            list[min] = temp;
        
        }
            
        long end = System.nanoTime();
        
        return (end - start);
    }
    
    /**
     * Shell sort implementation
     * @param list
     * @return 
     */
    static long shellSort(Integer[] list){
        long start = System.nanoTime();
    
        int gap = SIZE/2;
        while(gap > 0){
            for(int nextPos = gap; nextPos < SIZE; nextPos++){
                insert(list, nextPos, gap);
            }
            
            if(gap == 2) gap = 1;
            else gap = (int)(gap/2.2);
        
        }
        
        
        
        
        
        long end = System.nanoTime();
        
        return (end - start);
    }
    
    /**
     * We use this sub-function in the shell sort
     * @param list
     * @param nextPos
     * @param gap 
     */
    static void insert(Integer[] list, int nextPos, int gap){
        Integer nextVal = list[nextPos];
        
        while((nextPos > (gap-1)) && (nextVal.compareTo(list[nextPos - gap]) < 
                0)){
        list[nextPos] = list[nextPos - gap];
        
        nextPos -= gap;
        
        }
    list[nextPos] = nextVal;
    }
    
    
    /**
     * Merge sort implementation
     * @param list
     * @return 
     */
    static long mergeSort(Integer[] list){
        long start = System.nanoTime();
    
        if(list.length > 1){
            int halfSize = list.length/2;
            Integer[] leftTable = new Integer[halfSize];
            Integer[] rightTable = new Integer[list.length - halfSize];
            System.arraycopy(list, 0, leftTable, 0, halfSize);
            System.arraycopy(list, halfSize, rightTable, 0, list.length - 
                    halfSize);
        
            mergeSort(leftTable);
            mergeSort(rightTable);
            
            merge(list, leftTable, rightTable);
            
        }
        
        
       
        
        
        
        
        long end = System.nanoTime();
        
        return (end - start);
    }
    
  /**
   * We use this subfunction in the merge sort 
   * @param outputSequence
   * @param leftSequence
   * @param rightSequence 
   */
    static void merge(Integer[] outputSequence, Integer[] leftSequence, 
            Integer[] rightSequence){
        int i = 0;
        int j = 0;
        int k = 0;
        
        
        while(i < leftSequence.length && j < rightSequence.length){
            if(leftSequence[i].compareTo(rightSequence[j]) < 0){
                outputSequence[k++] = leftSequence[i++];
            } else {
                outputSequence[k++] = rightSequence[j++];
            }
        
            
        }    
            
        while(i < leftSequence.length){
            outputSequence[k++] = leftSequence[i++];
        }
            
        while(j < rightSequence.length){
            outputSequence[k++] = rightSequence[j++];
        }
            
            
            
        
    
    
    
    }
    
    
    
    
    
    
    /**
     * This function will check that the lists are in fact sorted
     * @param list1
     * @param list2
     * @param list3 
     */
    static void verify(Integer[] list1, Integer[] list2, Integer[] list3){
        
        for(int i = 0; i < (SIZE - 1); i++){
            if(list1[i+1] < list1 [i]){
                System.err.println("SORTING ERROR: selection sort");
                System.err.println("PROGRAM WILL BE TERMINATED");
                System.exit(0);
                
            }
        }
        for(int i = 0; i < (SIZE - 1); i++){
            if(list2[i+1] < list2 [i]){
                System.err.println("SORTING ERROR: shell sort");
                System.err.println("PROGRAM WILL BE TERMINATED");
                System.exit(0);
            }
        }
        for(int i = 0; i < (SIZE - 1); i++){
            if(list3[i+1] < list3 [i]){
                System.err.println("SORTING ERROR: merge sort");
                System.err.println("PROGRAM WILL BE TERMINATED");
                System.exit(0);
            }
        }
        
        System.out.println("Sorts validated");
        
    
    }
    
    
    /**
     * Make a report with the results of the timing
     * @param filename 
     */
    static void createReport(String filename, double[][] results){
    
        File outFile = new File(filename); 
       
   
        try {
          
            
            
            File inFile = new File(filename);
            PrintWriter fout = new PrintWriter(outFile);
            
            fout.println("SORTING RESULTS");
            fout.println("---------------");
            fout.println("                     Run1        Run2        Run3   "
                    + "      Average");
            fout.printf("Selection Sort       %.2f       ", results[0][0]);
            fout.printf("%.3f        ", results[1][0]);
            fout.printf("%.3f       ", results[2][0]);
            fout.printf("%.3f%n", results[3][0]);
            fout.printf("Shell Sort           %.2f       ", results[0][1]);
            fout.printf("%.3f        ", results[1][1]);
            fout.printf("%.3f       ", results[2][1]);
            fout.printf("%.3f%n", results[3][1]);
            fout.printf("Merge Sort           %.2f       ", results[0][2]);
            fout.printf("%.3f        ", results[1][2]);
            fout.printf("%.3f       ", results[2][2]);
            fout.printf("%.3f%n", results[3][2]);
            
      
            
         
            
            
               fout.close();
            
            
        } catch (Exception e) {
            System.err.println("Cannot open output file " + 
                    filename); 
                 
            
        } 
    
    }
    
    
    
}
