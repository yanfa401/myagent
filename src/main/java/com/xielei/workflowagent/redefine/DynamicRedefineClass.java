package com.xielei.workflowagent.redefine;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;

import com.xielei.workflowagent.base.ClassPoolUtil;

import javassist.CtClass;
import javassist.CtMethod;

/**
 * @author xielei
 * @date 2023/7/3 15:59
 */
public class DynamicRedefineClass {

    private final Instrumentation instrumentation;

    public DynamicRedefineClass(Instrumentation instrumentation) {
        this.instrumentation = instrumentation;
    }

    /**
     * todo 可能由zk来进行调用该方法, 成功redefine后就删除zk节点
     */
    public void redefineClass(String clazzName, String methodName, String javaCode) {
        try {
            CtClass ctClass = ClassPoolUtil.getClass(clazzName);
            Class<?> targetClazz = ctClass.toClass();
            // TODO: 2023/7/3 针对具体方法进行增强
            CtMethod method = ctClass.getDeclaredMethod(methodName);
            method.insertBefore(javaCode);

            ClassDefinition definition = new ClassDefinition(targetClazz, ctClass.toBytecode());
            instrumentation.redefineClasses(definition);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
