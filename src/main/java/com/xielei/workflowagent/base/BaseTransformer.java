package com.xielei.workflowagent.base;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @author xielei
 * @date 2023/7/3 13:47
 */
public class BaseTransformer implements ClassFileTransformer {

    private static final String bootStrapClassName = "com.iwhalecloud.zsmart.bss.bm.web.BmWebApplication";

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (className != null) {
            String currentClass = className.replaceAll("/", ".");
            if (bootStrapClassName.equals(currentClass)) {
                ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
                System.out.println("==> boot " + contextClassLoader);
                ClassPoolUtil.insertClassPath(contextClassLoader);
            }
        }
        return null;
    }
}
