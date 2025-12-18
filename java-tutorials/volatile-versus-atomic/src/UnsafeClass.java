import java.util.concurrent.atomic.AtomicInteger;

public class UnsafeClass {
    private volatile boolean done = false;

    // public void loop() throws InterruptedException {
    //     System.out.println("Entering loop method");
    //     for (int i = 0; i < 3; i++) {
    //         Thread.sleep(1000); // Sleep for 1 second.
    //     }
    //     System.out.println("setting done to true");
    //     done = true;
        /**
         * Even though the first thread has set done to true,
         * the second thread is still running.  Why is this?
         * 
         * Essentially what's happening is when a thread starts
         * executing its work it has a local cache of various variables
         * and what it does is then it doesn't have to go and retrieve
         * the program level value of a variable every single time,
         * it can just look at its local cache. When that happens though, say with one thread updating a variable and another thread reading its own local cache, it will not know when this thread makes the change.  How do you fix that? The first thread should write to a volatile member variable because when writing to something that's volatile, then any readers of that variable will read the latest write that has happened to that variable and they're not going to read any sort of cached value. The operating system has local versions of each of these done variables just for different threads but we need the operating system to read the same value.
         * 
         * The fix: Add the "volatile" keyword to the done variable,
         * which will cause each thread to not store the done variable
         * in a local cache but read its current value each time.
         * 
         * Whenever there are mutable data members that are accessed
         * across multiple threads, there needs to be someway to protect
         * reads and writes from them in some way.
         * 
         * volatile does not fix the problem of atomic operations
         * because volatile only enforces visibility of local variables,
         * it doesn't enforce that writes happen at particular times.
         */
    // }

    // public void waitToFinish() {
    //     System.out.println("waitToFinish entering");
    //     while (!done);
    //     System.out.println("waitToFinish exiting");
    // }

    /**
     * Example 2: Volatile doesn't enforce writes, but atomic does!
     * private int counter; // Int fields initialize to 0.
     * private volatile int counter; // This also won't work
     * In IntelliJ, the warning:
     * "Non-atomic operation on volatile field 'counter' happens"
     * What this is saying is that, if volatile is used here,
     * then one must expect that every operation that happens
     * on this variable is atomic. Otherwise, there will be
     * synchronization issues, even though the visibility issue
     * has been fixed.
     * 
     * The fix: add synchronized to the method increment,
     * and remove the 'volatile' modifier from the counter variable.
     * 
     * The better fix: synchronized is sometimes a little heavy
     * for incrementing integers, so the better way is to replace
     * int with AtomicInteger and to remove synchronized.
     */

    // AtomicInteger basically guarantees that when the increment
    // and get methods are executed, only atomic operations are performed,
    // which ensures thread safety.
    private AtomicInteger counter = new AtomicInteger(0);

    // Should AtomicInteger be marked with volatile?
    // The answer is no, because when the volatile keyword
    // is added, we are saying that all Threads should not
    // use local cache in order to change some sort of value.
    // What we're talking about is the actual value and not
    // the value that something points to. If AtomicInteger
    // was a primitive, then the volatile keyword would need
    // to be used.  Since AtomicInteger is an object reference
    // the value of the reference is not being changed, just
    // the data underneath the reference, the volatile keyword
    // isn't needed and it would be better if the final
    // keyword is used since final says immutable.
    // Immutability is a strong preference when dealing with
    // concurrency because then you don't need to worry about
    // other things like volatile, synchronized, etc. because
    // you have data immutability, and when you're dealing 
    // with shared data, it's really mutable data that
    // you need to be concerned about. So in this case, 
    // it is better to use final for an object reference
    // because if you were to make an object reference
    // volatile, that would mean you're changing the object
    // reference itself, not the data that the object
    // reference points to.

    public void increment() {
        this.counter.incrementAndGet();
    }

    public int get() {
        return this.counter.get();
    }
}
