package com.xielei.workflowagent.util;

import java.io.File;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import cn.hutool.core.io.file.FileAppender;
import cn.hutool.core.util.ZipUtil;

/**
 * FC记录CustOrder当前数据
 *
 * @author xielei
 * @date 2023/6/25 10:18
 */
public final class FCCustOrderChangeTracer {

    private FCCustOrderChangeTracer() {
        // empty
    }

    private static final String BASE_PATH = System.getenv("CATALINA_HOME") + "/FC_TRACE/";


    public static void trace(String fcName, Object obj, boolean start) {
        String suffix = ".end";
        if (start) {
            suffix = ".begin";
        }
        File file = new File(BASE_PATH + fcName + suffix);
        if (file.exists()) {
            file.delete();
        }

        FileAppender appender = new FileAppender(file, 16, true);
        appender.append(JSONObject.toJSONStringWithDateFormat(obj, "yyyy-MM-dd HH:mm:ss", SerializerFeature.PrettyFormat));
        appender.flush();
    }


    public static void zip() {
        ZipUtil.zip(BASE_PATH);
    }

}
