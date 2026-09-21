import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
public class SearchComplexity {
   
    static int linearSearch(int[] arr, int key, int[] comparisons) {
        comparisons[0] = 0;
        for (int i = 0; i < arr.length; i++) {
            comparisons[0]++;
            if (arr[i] == key) {
                return i;
            }
        }
        return -1;
    }
   
    static int binarySearch(int[] arr, int key, int[] comparisons) {
        int low = 0;
        int high = arr.length - 1;
        comparisons[0] = 0;
        while (low <= high) {
            comparisons[0]++;
            int mid = (low + high) / 2;
            if (arr[mid] == key) {
                return mid;
            }
            else if (arr[mid] < key) {
                low = mid + 1;
            }
            else {
                high = mid - 1;
            }
        }
        return -1;
    }
    
    public static void main(String[] args) {
        try {
            
            PrintWriter file =
                    new PrintWriter(new FileWriter("search_graph.csv"));
           
            file.println(
                    "InputSize,LinearBest,LinearAverage,LinearWorst," +
                    "BinaryBest,BinaryAverage,BinaryWorst"
            );
            
            System.out.println(
                    "InputSize\tLinearBest\tLinearAvg\tLinearWorst\t" +
                    "BinaryBest\tBinaryAvg\tBinaryWorst"
            );
           
            for (int n = 1000; n <= 10000; n += 1000) {
               
                int[] arr = new int[n];
                
                for (int i = 0; i < n; i++) {
                    arr[i] = i + 1;
                }
               
                int[] comp = new int[1];
                
                linearSearch(arr, arr[0], comp);
                int linearBest = comp[0];
               
                linearSearch(arr, arr[n - 1], comp);
                int linearWorst = comp[0];
                
                linearSearch(arr, arr[n / 2], comp);
                int linearAverage = comp[0];
                
                binarySearch(arr, arr[n / 2], comp);
                int binaryBest = comp[0];
                
                binarySearch(arr, arr[n - 1], comp);
                int binaryWorst = comp[0];
                
                binarySearch(arr, arr[n / 4], comp);
                int binaryAverage = comp[0];
                
                System.out.println(
                        n + "\t\t" +
                        linearBest + "\t\t" +
                        linearAverage + "\t\t" +
                        linearWorst + "\t\t" +
                        binaryBest + "\t\t" +
                        binaryAverage + "\t\t" +
                        binaryWorst
                );
                
                file.println(
                        n + "," +
                        linearBest + "," +
                        linearAverage + "," +
                        linearWorst + "," +
                        binaryBest + "," +
                        binaryAverage + "," +
                        binaryWorst
                );
            }
            
            file.close();
            System.out.println();
            System.out.println("Data saved to search_graph.csv");
        }
        catch (IOException e) {
            System.out.println("Error creating CSV file.");
            e.printStackTrace();
        }
    }
}