import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        Deque<Integer> stack = new ArrayDeque<>();

        int loopCounter = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < loopCounter; i++) {
            stack.push(Integer.parseInt(scanner.nextLine()));
        }

        stack.forEach(element -> System.out.println(stack.pop()));
    }
}