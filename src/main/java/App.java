import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class App {

    public static AtomicInteger count3 = new AtomicInteger(0);
    public static AtomicInteger count4 = new AtomicInteger(0);
    public static AtomicInteger count5 = new AtomicInteger(0);

    public static final String[] alphabet = {"a", "b", "c"};

    public static void main(String[] args) throws InterruptedException {

        boolean test = isAscending("caa");

        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText(String.join("", alphabet), 3 + random.nextInt(3));
        }

        Thread palindrome = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                String str = texts[i];
                if (isPalindrome(str)) {
                    switch (str.length()) {
                        case 3:
                            count3.incrementAndGet();
                            break;
                        case 4:
                            count4.incrementAndGet();
                            break;
                        case 5:
                            count5.incrementAndGet();
                            break;
                    }
                }
            }
        });

        Thread oneSymbol = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                String str = texts[i];
                if (isOneSymbol(str)) {
                    switch (str.length()) {
                        case 3:
                            count3.incrementAndGet();
                            break;
                        case 4:
                            count4.incrementAndGet();
                            break;
                        case 5:
                            count5.incrementAndGet();
                            break;
                    }
                }
            }
        });

        Thread accessing = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                String str = texts[i];
                if (isAscending(str)) {
                    switch (str.length()) {
                        case 3:
                            count3.incrementAndGet();
                            break;
                        case 4:
                            count4.incrementAndGet();
                            break;
                        case 5:
                            count5.incrementAndGet();
                            break;
                    }
                }
            }
        });

        palindrome.start();
        oneSymbol.start();
        accessing.start();

        accessing.join();
        oneSymbol.join();
        palindrome.join();

        System.out.println("Красивых слов с длиной 3: " + count3 + " шт");
        System.out.println("Красивых слов с длиной 4: " + count4 + " шт");
        System.out.println("Красивых слов с длиной 5: " + count5 + " шт");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isPalindrome(String str) {
        return str.equals(new StringBuilder(str).reverse().toString());
    }

    public static boolean isOneSymbol(String str) {
        char[] array = str.toCharArray();
        char current = array[0];
        for (int i = 1; i < array.length; i++) {
            if (current != array[i])
                return false;
        }
        return true;
    }

    //Алгоритм для алфавита, где буквы в любом порядке (Например алфавит cba)
    public static boolean isAscending(String str) {
        for (int i = 0; i < alphabet.length; i++) {
            int lastIndex = str.lastIndexOf(alphabet[i]);
            if (lastIndex == -1) continue;

            //Проверяем символы идущие после текущего
            for (int j = i + 1; j < alphabet.length; j++) {
                int index = str.indexOf(alphabet[j]);

                if (index == -1) continue;

                if (index < lastIndex) return false;
            }
        }
        return true;
    }

}
