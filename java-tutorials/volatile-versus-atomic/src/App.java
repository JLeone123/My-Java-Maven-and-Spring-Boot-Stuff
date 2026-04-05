import java.util.concurrent.Executors;

public class App {
    /**
     * The keyword volatile has a special meaning in the context of multithreading. 
     * If an application is single-threaded, using volatile isn't necessary and can hamper performance; 
     * however, it makes sense when variables need to be shared across threads
     */
    // private static volatile int myInt = 2;
    // Tutorial: https://www.youtube.com/watch?v=31s_DzrkqZc
    public static void main(String[] args) throws Exception {
        // System.out.println(myInt);

        // Example 1: Making a variable that will get modified by
        // multiple threads and not marking it volatile
        // UnsafeClass myClass = new UnsafeClass();
        // final int numThreads = 2;
        // try (final var executorService = Executors.newFixedThreadPool(numThreads)) {
        //     executorService.submit(() -> {
        //         try {
        //             myClass.loop();
        //         } catch (InterruptedException e) {
        //             throw new RuntimeException(e);
        //         }
        //     });
        //     executorService.submit(myClass::waitToFinish);
        // }

        // Example 2: Volatile doesn't enforce writes, but atomic does!
        final UnsafeClass myClass = new UnsafeClass();
        final int numThreads = 5;
        final int numIters = 10_000;
        try (final var executorService = Executors.newFixedThreadPool(numThreads)) {
            for (int i = 0; i < numThreads; i++) {
                executorService.submit(() -> {
                    for (int j = 0; j < numIters; j++) {
                        myClass.increment();
                    }
                });
            }
        }
        System.out.println(myClass.get());
    }
}
