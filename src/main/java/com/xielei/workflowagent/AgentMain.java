package com.xielei.workflowagent;

import java.lang.instrument.Instrumentation;


/**
 * 以 loadAgent 方法动态 attach 到目标 JVM 上，这种情况下，会执行 agentmain方法。
 * @see VirtualMachine#loadAgent(java.lang.String, java.lang.String)
 *
 * @author xielei
 * @date 2023/7/3 15:44
 */
public class AgentMain {

    public static void agentmain(String agentArgs, Instrumentation inst) {

    }
}
