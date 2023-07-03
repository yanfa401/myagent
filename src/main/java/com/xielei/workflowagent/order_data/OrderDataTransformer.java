package com.xielei.workflowagent.order_data;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;

import com.xielei.workflowagent.base.ClassPoolUtil;

import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;

/**
 * @author xielei
 * @date 2023/6/25 17:45
 */
public class OrderDataTransformer implements ClassFileTransformer {

    private static final List<String> CANDIDATE_TRANS_CLASS_LIST = new ArrayList<>();

    static {
        CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.order.entity.OrderItem");
        CANDIDATE_TRANS_CLASS_LIST.add("com.iwhalecloud.zsmart.bss.bm.oc.order.entity.ProdInstOrder");
    }


    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        try {
            if (className != null) {
                String currentClass = className.replaceAll("/", ".");
                if (CANDIDATE_TRANS_CLASS_LIST.contains(currentClass)) {
                    CtClass ctClass = ClassPoolUtil.getClass(currentClass);
                    CtField[] fields = ctClass.getDeclaredFields();
                    for (CtField field : fields) {
                        String fieldName = field.getName();
                        String methodName = getSetterMethod(field);
                        CtMethod declaredMethod = ctClass.getDeclaredMethod(methodName);
                        declaredMethod.insertBefore("com.xielei.workflowagent.order_data.OrderDataTracer.trace(\"" + methodName + "\", \"" + fieldName + "\", $1);");
                    }
                    return ctClass.toBytecode();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }


    /**
     * 获取setter方法的名称
     */
    private static String getSetterMethod(CtField field) {
        String fieldName = field.getName();
        String firstLetter = fieldName.substring(0, 1).toUpperCase();
        return "set" + firstLetter + fieldName.substring(1);
    }
}
