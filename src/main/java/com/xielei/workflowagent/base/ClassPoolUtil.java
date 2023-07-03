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

    /**
     * 获取CtClass
     */
    public static CtClass getClass(String clazzName) throws NotFoundException {
        return CLASS_POOL.get(clazzName);
    }

    public static void insertClassPath(ClassLoader classLoader) {
        CLASS_POOL.insertClassPath(new LoaderClassPath(classLoader));
    }
}
