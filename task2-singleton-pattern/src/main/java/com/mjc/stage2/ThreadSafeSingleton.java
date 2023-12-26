package com.mjc.stage2;

public class ThreadSafeSingleton {
    private static volatile ThreadSafeSingleton instance;
    public String sharedDatabase;

    private ThreadSafeSingleton(String sharedDatabase) {
        this.sharedDatabase = sharedDatabase;
    }

    public static ThreadSafeSingleton getInstance(String sharedDatabase) {
        ThreadSafeSingleton result = instance;
        if (result != null)
            return result;
        synchronized(ThreadSafeSingleton.class) {
            if (instance == null)
                instance = new ThreadSafeSingleton(sharedDatabase);
            return instance;
        }
    }
}
