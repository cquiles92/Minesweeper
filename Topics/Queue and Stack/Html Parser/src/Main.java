import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

class Main {

    public static void main(String[] args) {
        String data = new Scanner(System.in).nextLine();

        Deque<String> deque = new ArrayDeque<>();
        parse(data, deque);
        while (!deque.isEmpty()) {
            System.out.println(deque.pollFirst());
        }
    }

    private static void parse(String data, Deque<String> deque) {
        if (data.isEmpty()) {
            return;
        }

        String subString;
        String newData;
        if (data.charAt(0) == '<') {
            subString = data.substring(1, data.indexOf('>') + 1);
            if (data.indexOf("</" + subString) == data.length() - subString.length() - 2) {
                newData = data.replace("<" + subString, "").replace("</" + subString, "");
                deque.offerFirst(newData);
                parse(newData, deque);
            } else {
                String temp = data.substring(0, data.indexOf("</" + subString) + subString.length() + 2);
                newData = data.replace(temp, "");
                parse(newData, deque);
                parse(temp, deque);
            }
        }
    }
}