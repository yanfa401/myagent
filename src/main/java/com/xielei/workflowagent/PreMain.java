package com.xielei.workflowagent;

import java.lang.instrument.Instrumentation;

import com.xielei.workflowagent.base.BaseTransformer;
import com.xielei.workflowagent.fc.FCTransformer;
import com.xielei.workflowagent.order_data.OrderDataTransformer;
import com.xielei.workflowagent.util.DBUtil;

/**
 * @author xielei
 * @date 2023/6/21 15:37
 */
public class PreMain {

    /**
     * @see https://www.jianshu.com/p/c7cd105daabc
     */
    public static void premain(String agentArgs, Instrumentation instrumentation) {
        DBUtil.init();
        instrumentation.addTransformer(new BaseTransformer());
        instrumentation.addTransformer(new FCTransformer());
        instrumentation.addTransformer(new OrderDataTransformer());
    }
}
