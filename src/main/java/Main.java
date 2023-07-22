import java.util.*;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();

        String[] texts = new String[1000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateRoute("RLRFR", 100);
        }
        for (String text : texts) {
            Thread thread = new Thread(() -> {
                int count = 0;
                for (int i = 0; i < text.length(); i++) {
                    if (text.charAt(i) == 'R') count++;
                }
                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(count)) {
                        sizeToFreq.put(count, sizeToFreq.get(count) + 1);
                    } else {
                        sizeToFreq.put(count, 1);
                    }
                }

            });
            threads.add(thread);
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        Map.Entry<Integer, Integer> maxKey = sizeToFreq
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get();
        System.out.println("Самое частое количество повторений " + maxKey.getKey() + " (встретилось " + maxKey.getValue() + " раз)");
        System.out.println("LДругие размеры: ");
        sizeToFreq
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEachOrdered(x -> System.out.println(x.getKey() + "( " + x.getValue() + " раз)"));

    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}
