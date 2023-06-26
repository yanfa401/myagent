package com.xielei.workflowagent.order_data;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

import com.xielei.workflowagent.util.DBUtil;
import com.xielei.workflowagent.util.FCThreadLocalUtil;

/**
 * @author xielei
 * @date 2023/6/25 17:17
 */
public final class OrderDataTracer {

    private OrderDataTracer() {
        // empty
    }

    private static final AtomicInteger atomicInteger = new AtomicInteger(0);

    static {
        // 启动agent的时候清表
        try {
            DBUtil.DB.execute("delete from trajectory");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void trace(String methodName, String fieldName, Object value) {
        String currentClassName = FCThreadLocalUtil.get();
        if (currentClassName != null) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            for (StackTraceElement stackTraceElement : stackTrace) {
                String className = stackTraceElement.getClassName();
                if (currentClassName.equals(className)) {
                    int lineNumber = stackTraceElement.getLineNumber();
                    try {
                        DBUtil.DB.execute("INSERT INTO trajectory VALUES(?, ?, ?, ?, ?, ?, ?)", "998", fieldName, atomicInteger.getAndIncrement(), className, lineNumber, methodName, value);
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }
}
