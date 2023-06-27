package com.xielei.workflowagent.order_data;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

import com.xielei.workflowagent.util.DBUtil;
import com.xielei.workflowagent.util.FCThreadLocalUtil;
import com.xielei.workflowagent.util.ThreadPoolUtil;

/**
 * @author xielei
 * @date 2023/6/25 17:17
 */
public final class OrderDataTracer {

    private OrderDataTracer() {
        // empty
    }

    private static final AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void trace(String methodName, String fieldName, Object value) {
        String currentClassName = FCThreadLocalUtil.get();
        if (currentClassName != null) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            for (StackTraceElement stackTraceElement : stackTrace) {
                String className = stackTraceElement.getClassName();
                if (currentClassName.equals(className)) {
                    int lineNumber = stackTraceElement.getLineNumber();
                    ThreadPoolUtil.apply(new OrderDataTracerTask(fieldName, className, lineNumber, methodName, value));
                    break;
                }
            }
        }
    }

    private static class OrderDataTracerTask implements Runnable {

        private final String fieldName;

        private final String className;

        private final int lineNumber;

        private final String methodName;

        private final Object value;

        public OrderDataTracerTask(String fieldName, String className, int lineNumber, String methodName, Object value) {
            this.fieldName = fieldName;
            this.className = className;
            this.lineNumber = lineNumber;
            this.methodName = methodName;
            this.value = value;
        }

        @Override
        public void run() {
            try {
                DBUtil.DB.execute("insert into trajectory values(?, ?, ?, ?, ?, ?, ?)", "998", fieldName, atomicInteger.getAndIncrement(), className, lineNumber, methodName, value);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
