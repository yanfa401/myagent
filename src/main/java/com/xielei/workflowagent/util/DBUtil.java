package com.xielei.workflowagent.util;

import cn.hutool.db.Db;

/**
 * @author xielei
 * @date 2023/6/26 14:02
 */
public abstract class DBUtil {

    public static final Db DB = Db.use();
}
