import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
//import java.util.function.*;
import java.util.regex.*;
//import java.util.stream.*;
//import static java.util.stream.Collectors.joining;
//import static java.util.stream.Collectors.toList;


class Result {

  public static int entryTime(String s, String keypad) {
    
    int rcount = 0;
    int ccount = 0;
    int[] row_of = new int[10];
    int[] col_of = new int[10];
    int totalTime = 0;
    
    // Get row_of and col_of of each key in the keypad
    //
    // row_of[N] = gives the row    number of N in the 3x3 keypad. Can either be 0, 1, or 2
    // col_of[N] = gives the column number of N in the 3x3 keypad. Can either be 0, 1, or 2
    //
    for(int i = 0; i < keypad.length(); i++) {
      int intval = Character.getNumericValue(keypad.charAt(i));
      row_of[intval] = rcount;
      col_of[intval] = ccount;
      if (ccount == 2) {
        rcount++;
        ccount = 0;
      }
      else {
        ccount++;
      }
    }
    // Print all rows and columns
    //for(int i = 1; i < 10; i++) {
    //  System.out.print(i + ": ROW " + row_of[i] + " COL " + col_of[i] + "\n");
    //}
    
    // We will divide s into different "steps":
    // 
    // e.g. s = "45123"
    // 
    //   4        5        1        2        3
    //   |-step 1-|-step 2-|-step 3-|-step 4-|
    //
    // If s is 5-digits long, then we only need 5-1=4 steps
    //
    // For each step, calculate the time from source to destination
    //
    // To calculate the time from source to destination, we need to compute:
    //   - how many rows apart they are (row_distance), and
    //   - how many columns apart they are (col_distance)
    //
    //      If row_distance is 2 or col_distance is 2, time is 2
    // else if row_distance is 1 or col_distance is 1, time is 1
    // Else, then it means destination is same as source, time is 0
    //
    // Checking if time is 2 takes priority because a destination can be 2 rows and 1 column from the source
    //   or 2 columns and 1 row from the source.
    //
    //               +--------dest
    //               V
    //         +---+---+---+
    //         |   | 5 |   |
    //         +---+---+---+
    //         |   |   |   |
    //         +---+---+---+
    // source->| 1 |   |   |
    //         +---+---+---+
    //
    // In this example, 5 is 2 rows and 1 column from the source. The time should be 2 here.
    //   But if we use the 2nd if-statement first, then it will be true because col_distance is 1, and time
    //     will be 1.
    //
    for(int i = 0; i < s.length() - 1; i++) {
      int src  = Character.getNumericValue(s.charAt(i));
      int dest = Character.getNumericValue(s.charAt(i+1));

      int row_distance = Math.abs(row_of[src] - row_of[dest]);
      int col_distance = Math.abs(col_of[src] - col_of[dest]);

      if ((row_distance == 2) || (col_distance == 2)) {
        totalTime += 2;
      }
      else if ((row_distance == 1) || (col_distance == 1)) {
        totalTime += 1;
      }
      else {
        totalTime += 0;
      }
    }
    return totalTime;
  }

}

public class Solution {
  public static void main(String[] args) throws IOException {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

    String s = bufferedReader.readLine();

    String keypad = bufferedReader.readLine();

    int result = Result.entryTime(s, keypad);

    bufferedWriter.write(String.valueOf(result));
    bufferedWriter.newLine();

    bufferedReader.close();
    bufferedWriter.close();
  }
}

