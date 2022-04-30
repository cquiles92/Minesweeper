import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Queue<Integer> firstQueue = new ArrayDeque();
        Queue<Integer> secondQueue = new ArrayDeque();

        int firstLoad = 0;
        int secondLoad = 0;

        Scanner scanner = new Scanner(System.in);
        int loopCounter = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < loopCounter; i++) {
            String[] input = scanner.nextLine().split(" ");

            if (firstLoad <= secondLoad) {
                firstQueue.offer(Integer.parseInt(input[0]));
                firstLoad += Integer.parseInt(input[1]);
            } else {
                secondQueue.offer(Integer.parseInt(input[0]));
                secondLoad += Integer.parseInt(input[1]);
            }
        }

        while (firstQueue.size() != 0) {
            System.out.print(firstQueue.poll() + " ");
        }
        System.out.println();

        while (secondQueue.size() != 0) {
            System.out.print(secondQueue.poll() + " ");
        }
    }
}