package com.xielei.workflowagent.util;

/**
 * FCThreadLocalUtil
 *
 * @author xielei
 * @date 2023/6/25 10:06
 */
public final class FCThreadLocalUtil {

    private FCThreadLocalUtil() {
        // empty
    }

    private static final ThreadLocal<String /* FC完全限定名 */> FC_NAME_THREAD_LOCAL = new ThreadLocal<>();


    public static String get() {
        return FC_NAME_THREAD_LOCAL.get();
    }


    public static void set(String name) {
        FC_NAME_THREAD_LOCAL.set(name);
    }

    public static void remove() {
        FC_NAME_THREAD_LOCAL.remove();
    }


}
