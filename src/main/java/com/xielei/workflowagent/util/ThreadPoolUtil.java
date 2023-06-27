package com.xielei.workflowagent.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author xielei
 * @date 2023/6/27 10:06
 */
public abstract class ThreadPoolUtil {

    public ThreadPoolUtil() {
    }

    private static final ExecutorService EXECUTOR_SERVICE = new ThreadPoolExecutor(32, 32, 5, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1000), new ThreadPoolExecutor.CallerRunsPolicy());

    public static void apply(Runnable task) {
        EXECUTOR_SERVICE.execute(task);
    }
}
