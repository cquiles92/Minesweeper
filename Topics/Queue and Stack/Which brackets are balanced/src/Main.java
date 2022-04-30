import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        System.out.println(isBalanced(input));

        scanner.close();
    }

    public static boolean isBalanced(String input) {
        Deque<Character> stack = new ArrayDeque<>();

        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            switch (currentChar) {
                case '(':
                case '[':
                case '{':
                    stack.offerFirst(currentChar);
                    break;
                case ')':
                    if (stack.size() > 0) {
                        char top = stack.pollFirst();
                        if (top != '(') {
                            return false;
                        }
                    } else {
                        return false;
                    }
                    break;
                case ']':
                    if (stack.size() > 0) {
                        char top = stack.pollFirst();
                        if (top != '[') {
                            return false;
                        }
                    } else {
                        return false;
                    }
                    break;
                case '}':
                    if (stack.size() > 0) {
                        char top = stack.pollFirst();
                        if (top != '{') {
                            return false;
                        }
                    } else {
                        return false;
                    }
                    break;
                default:
                    System.out.println("Unexpected Char");
                    return false;
            }
        }

        return stack.size() == 0;
    }
}