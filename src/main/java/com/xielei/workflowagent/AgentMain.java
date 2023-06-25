package com.xielei.workflowagent;

import java.lang.instrument.Instrumentation;

/**
 * @author xielei
 * @date 2023/6/21 15:37
 */
public class AgentMain {

    /**
     * @see https://www.jianshu.com/p/c7cd105daabc
     */
    public static void premain(String agentArgs, Instrumentation instrumentation) {
        System.out.println("premain");
        FCTransformer fcTransformer = new FCTransformer();
        instrumentation.addTransformer(fcTransformer);
    }
}
