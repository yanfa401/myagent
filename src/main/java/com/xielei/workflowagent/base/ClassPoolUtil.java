package com.xielei.workflowagent.base;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.LoaderClassPath;
import javassist.NotFoundException;

/**
 * @author xielei
 * @date 2023/7/3 13:50
 */
public final class ClassPoolUtil {

    public ClassPoolUtil() {
    }

    private static final ClassPool CLASS_POOL = ClassPool.getDefault();

    private static ClassLoader contextLoader;

    /**
     * 获取CtClass
     */
    public static CtClass getClass(String clazzName) throws NotFoundException {
        return CLASS_POOL.get(clazzName);
    }

    public static void insertClassPath(ClassLoader classLoader) {
        contextLoader = classLoader;
        CLASS_POOL.insertClassPath(new LoaderClassPath(classLoader));
    }

    public static ClassLoader getContextLoader() {
        return contextLoader;
    }
}
