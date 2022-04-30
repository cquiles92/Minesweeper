import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

class Main {
    private static final String PUSH = "push";
    private static final String POP = "pop";
    private static final String MAX = "max";

    public static void main(String[] args) {
        // put your code here
        Deque<Integer> stack = new ArrayDeque<>();
        Deque<Integer> maxStack = new ArrayDeque<>();
        Scanner scanner = new Scanner(System.in);

        int loopCounter = Integer.parseInt(scanner.nextLine());
        maxSequence(stack, maxStack, scanner, loopCounter);
    }

    private static void maxSequence(Deque<Integer> stack, Deque<Integer> maxStack, Scanner scanner, int loopCounter) {
        for (int i = 0; i < loopCounter; i++) {
            String command = scanner.next();
            switch (command) {
                case PUSH:
                    int value = Integer.parseInt(scanner.next());
                    pushOnStack(value, stack, maxStack);
                    break;
                case POP:
                    popOnStack(stack, maxStack);
                    break;
                case MAX:
                    maxOfStack(maxStack);
                    break;
                default:
                    System.out.println("ERROR");
            }
            scanner.nextLine();
        }
    }

    private static void pushOnStack(int value, Deque<Integer> stack, Deque<Integer> maxStack) {
        stack.offerFirst(value);
        if (maxStack.size() == 0) {
            maxStack.offerFirst(value);
        } else {
            maxStack.offerFirst(Math.max(value, maxStack.peekFirst()));
        }
    }

    private static void popOnStack(Deque<Integer> stack, Deque<Integer> maxStack) {
        stack.pollFirst();
        maxStack.pollFirst();
    }

    private static void maxOfStack(Deque<Integer> maxStack) {
        System.out.println(maxStack.peekFirst());
    }
}