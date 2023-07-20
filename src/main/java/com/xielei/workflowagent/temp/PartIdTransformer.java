package com.xielei.workflowagent.temp;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;

import com.xielei.workflowagent.base.ClassPoolUtil;

import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;

/**
 * 针对partId字段的增强
 * 因为目前的zdaas对分表入库必须会检测partId字段是否存在,然鹅我们由于历史原因代码里并不存在该字段.
 * 手动去编写该字段极其复杂,不如通过字节码批量生成.
 *
 * @author xielei
 * @date 2023/7/19 16:16
 */
public class PartIdTransformer implements ClassFileTransformer {

    /**
     * 黑名单列表
     */
    private static final List<String> excludedList = new ArrayList<>();

    /**
     * 白名单列表
     */
    private static final List<String> includedList = new ArrayList<>();

    static {
        // 黑名单列表
        excludedList.add("com.iwhalecloud.zsmart.bss.bm.oc.order.model.CcEventBatchDo");
        excludedList.add("com.iwhalecloud.zsmart.bss.bm.oc.order.model.CdrGenerateProcessLogDo");

        // 白名单列表
        includedList.add("com.iwhalecloud.zsmart.bss.bm.oc.workflow.model.BusinessFlowProcessDo");
        includedList.add("com.iwhalecloud.zsmart.bss.bm.oc.workflow.model.BmeCompProcDo");
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        try {
            if (className != null) {
                String currentClass = className.replaceAll("/", ".");

                // 黑名单列表里的直接结束
                if (excludedList.contains(currentClass)) {
                    return null;
                }

                // 增强model包下的属性 || 白名单
                if (currentClass.startsWith("com.iwhalecloud.zsmart.bss.bm.oc.order.model") || includedList.contains(currentClass)) {
                    CtClass ctClass = ClassPoolUtil.getClass(currentClass);
                    boolean find = false;
                    for (CtField f : ctClass.getDeclaredFields()) {
                        if ("partId".equals(f.getName())) {
                            find = true;
                            break;
                        }
                    }
                    // 如果该表已经存在partId字段, 则不增强
                    if (!find) {
                        CtField partIdF = CtField.make("private int partId = 0;", ctClass);
                        ctClass.addField(partIdF);

                        // 添加一个getter方法
                        CtMethod ctMethod = CtNewMethod.getter("getPartId", partIdF);
                        ctClass.addMethod(ctMethod);

                        // 添加一个setter方法
                        ctMethod = CtNewMethod.setter("setPartId", partIdF);
                        ctClass.addMethod(ctMethod);
                        return ctClass.toBytecode();
                    }
                    else {
                        return null;
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }
}
