package com.xielei.workflowagent.util;

import java.sql.SQLException;

import cn.hutool.db.Db;

/**
 * @author xielei
 * @date 2023/6/26 14:02
 */
public abstract class DBUtil {

    public static final Db DB = Db.use();

    public static void init() {
        // 启动agent的时候清表
        try {
            DBUtil.DB.execute("delete from trajectory");
            DBUtil.DB.execute("delete from fc_compare");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
