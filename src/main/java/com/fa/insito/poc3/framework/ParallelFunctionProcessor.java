package com.fa.insito.poc3.framework;

import com.google.common.base.Preconditions;
import org.joda.time.DateMidnight;

import java.util.Map;
import java.util.concurrent.*;

public class ParallelFunctionProcessor {

    private ExecutorService threadExecutor;

    public ParallelFunctionProcessor(int threadCount) {
        threadExecutor = Executors.newFixedThreadPool(threadCount, new ThreadFactory() {
            private int id = 0;
            private ThreadGroup group = new ThreadGroup("Parallel Function Processor (PFP) Thread Group");

            @Override
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(group, runnable, "PFP-Thread-" + id++);
                thread.setPriority(Thread.MIN_PRIORITY);
                thread.setDaemon(true);
                return thread;
            }
        });
        registerShutdownHook();
    }

    private void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                threadExecutor.shutdown();
            }
        }));
    }

    public <K, O> Map<K, O> performParallelProcessing(
            final Map<K, ? extends UnitOfWork<O>> unitsOfWork) {

        final CountDownLatch latch = new CountDownLatch(unitsOfWork.size());
        final Map<K, O> outputs = new ConcurrentHashMap<K, O>(unitsOfWork.size());

        for (final K key : unitsOfWork.keySet()) {
            Runnable job = new Runnable() {
                @Override
                public void run() {
                    try {
                        UnitOfWork<O> unitOfWork = unitsOfWork.get(key);
                        O output = unitOfWork.execute();
                        outputs.put(key, output);
                    } finally {
                        latch.countDown();
                    }
                }
            };
            threadExecutor.execute(job);
        }
        try {
            latch.await();
            return outputs;
        } catch (InterruptedException e) {
            throw new RuntimeException("Echec de l'exécution", e);
        }
    }

    public <K, I, O> Map<K, O> performParallelProcessing(
            final DateMidnight dateMarche,
            final Map<K, I> inputs,
            final FinancialFunction<I, O> function) {

        Preconditions.checkNotNull(inputs);
        Preconditions.checkNotNull(function);

        final CountDownLatch latch = new CountDownLatch(inputs.size());
        final Map<K, O> outputs = new ConcurrentHashMap<K, O>(inputs.size());

        for (final K key : inputs.keySet()) {
            Runnable job = new Runnable() {
                @Override
                public void run() {
                    try {
                        O output = function.compute(dateMarche, inputs.get(key));
                        outputs.put(key, output);
                    } finally {
                        latch.countDown();
                    }
                }
            };
            threadExecutor.execute(job);
        }
        try {
            latch.await();
            return outputs;
        } catch (InterruptedException e) {
            throw new RuntimeException("Echec de l'exécution", e);
        }

    }

}
