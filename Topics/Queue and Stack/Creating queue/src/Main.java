import java.util.*;

public class Main {

    public static void main(String[] args) {
        // write your code here
        Integer[] arr = {2, 0, 1, 7};
        Queue<Integer> queue = new ArrayDeque<>(Arrays.asList(arr));

        System.out.println(queue);
    }
}