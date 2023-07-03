package com.xielei.workflowagent;

import java.lang.instrument.Instrumentation;

import com.xielei.workflowagent.base.BaseTransformer;
import com.xielei.workflowagent.fc.FCTransformer;
import com.xielei.workflowagent.order_data.OrderDataTransformer;
import com.xielei.workflowagent.redefine.DynamicRedefineClass;
import com.xielei.workflowagent.util.DBUtil;

/**
 * premain()的启动是跟着 JVM 启动参数 -javaagent:xxx.jar 的形式随着 JVM 一起启动，这种情况下，会调用 premain方法，并且是在主进程的 main方法之前执行。
 *
 * @author xielei
 * @date 2023/6/21 15:37
 */
public class PreMain {

    /**
     * @see https://www.jianshu.com/p/c7cd105daabc
     */
    public static void premain(String agentArgs, Instrumentation instrumentation) {
        DBUtil.init();
        // 加载基础Transformer, 包含获取被增强项目的启动类路径, 获取上下文ClassLoader
        instrumentation.addTransformer(new BaseTransformer());

        // 增强FC, 获取修改前后订单json数据比对
        instrumentation.addTransformer(new FCTransformer());

        // 增强Order数据, 记录各个数据的轨迹
        instrumentation.addTransformer(new OrderDataTransformer());

        // 动态重定义class的类
        DynamicRedefineClass dynamicRedefineClass = new DynamicRedefineClass(instrumentation);
    }
}
